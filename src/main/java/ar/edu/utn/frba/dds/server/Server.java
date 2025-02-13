package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.dominio.services.broker.BrokerInit;
import ar.edu.utn.frba.dds.dominio.services.cronjobs.CrontasksScheduler;
import ar.edu.utn.frba.dds.utils.Initializer;
import ar.edu.utn.frba.dds.utils.JavalinRenderer;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.server.handlers.*;
import ar.edu.utn.frba.dds.middlewares.AuthMiddleware;
import io.javalin.micrometer.MicrometerPlugin;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if (app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void init() {
        if (app == null) {
            /*final StatsDClient statsd = new NonBlockingStatsDClient(new NonBlockingStatsDClientBuilder());

            app.before(ctx -> ctx.attribute("start-time", System.nanoTime()));

            app.after(ctx -> {
                long startTime = ctx.attribute("start-time");
                long durationMs = (System.nanoTime() - startTime) / 1_000_000;
                statsd.recordExecutionTime("request.latency", durationMs);
            });*/

            final var metricsUtils = new DDMetricsUtils("transferencias");
            final var registry = metricsUtils.getRegistry();

            // Metricas
            final var myGauge = registry.gauge("dds.unGauge", new AtomicInteger(0));

            // Config
            final var micrometerPlugin = new MicrometerPlugin(config -> config.registry = registry);

            int port = Integer.parseInt(System.getProperty("port", "7777"));
            app = Javalin.create(config(micrometerPlugin)).start(port);

            AuthMiddleware.apply(app);
            AppHandlers.applyHandlers(app);
            Router.init(app);

            app.exception(AccessDeniedException.class, (e, ctx) -> {
                ctx.status(403);
                ctx.render("Prohibido.hbs");
            });

            Initializer.init();

            BrokerInit.init();

            CrontasksScheduler scheduler = new CrontasksScheduler();
            scheduler.start();

            app.get("/number/{number}", ctx -> {
                var number = ctx.pathParamAsClass("number", Integer.class).get();
                myGauge.set(number);
                registry.counter("transferencias","status","ok").increment();
                ctx.result("updated number: " + number.toString());
            });
        }
    }

    private static Consumer<JavalinConfig> config(MicrometerPlugin micrometerPlugin) {
        return config -> {
            // Configuración para servir archivos estáticos desde /public y /uploads
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";  // Ruta base para /public
                staticFiles.directory = "/public";  // Directorio de archivos estáticos
            });
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/uploads";  // Ruta base para /uploads
                staticFiles.directory = "/public/uploads";   // Directorio de las imágenes y otros recursos subidos
                staticFiles.location = io.javalin.http.staticfiles.Location.CLASSPATH;
            });

            // Configuración de renderer de Handlebars
            config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
                Handlebars handlebars = new Handlebars();

                // Helper para comparar igualdad
                handlebars.registerHelper("eq", (context14, options) -> {
                    if (context14 == null || options.params.length < 1 || options.params[0] == null) {
                        return false;
                    }
                    // Convertimos ambos valores a String para evitar problemas de tipo
                    String value1 = context14.toString().trim();
                    String value2 = options.params[0].toString().trim();
                    return value1.equals(value2);
                });

                handlebars.registerHelper("formatDateTime", (Helper<LocalDateTime>) (context1, options) -> {
                    if (context1 == null) {
                        return "Fecha no disponible";
                    }
                    // Define el formato que desees para mostrar la fecha y hora
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    return context1.format(formatter);
                });

                // Helper para condiciones
                handlebars.registerHelper("ifCond", (context12, options) -> {
                    if (context12 == null || options.params.length < 1) {
                        return options.inverse(null);
                    }

                    // Obtener valores para comparar
                    String contextStr = context12.toString().trim();
                    String tipo = options.param(0).toString().trim();

                    if (contextStr.equalsIgnoreCase(tipo)) {
                        return options.fn(options.context); // Usa options.data para pasar el contexto original
                    }

                    return options.inverse(context12);
                });

                // Helper para depuración
                handlebars.registerHelper("debug", (context13, options) -> {
                    System.out.println("Debugging: " + context13);
                    return "";
                });

                // Compilación y renderización de la plantilla
                Template template;
                try {
                    template = handlebars.compile("templates/" + path.replace(".hbs", ""));
                    return template.apply(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    context.status(HttpStatus.NOT_FOUND);
                    return "No se encuentra la página indicada...";
                }
            }));

            config.registerPlugin(micrometerPlugin);
        };
    }
}
