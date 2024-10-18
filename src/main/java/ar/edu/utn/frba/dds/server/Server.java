package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.utils.Initializer;
import ar.edu.utn.frba.dds.utils.JavalinRenderer;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.server.handlers.*;
import ar.edu.utn.frba.dds.middlewares.AuthMiddleware;

import java.io.IOException;
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
            int port = Integer.parseInt(System.getProperty("port", "7777"));
            app = Javalin.create(config()).start(port);

            AuthMiddleware.apply(app);
            AppHandlers.applyHandlers(app);
            Router.init(app);


            app.exception(AccessDeniedException.class, (e, ctx) -> {
                ctx.status(403);
                ctx.render("Prohibido.hbs");
            });

            Initializer.init();
        }
    }
    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });

            config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
                Handlebars handlebars = new Handlebars();
                handlebars.registerHelper("eq", new Helper<Object>() {
                    @Override
                    public Object apply(Object context, Options options) {
                        if (context == null || options.params.length < 1) {
                            return false;
                        }
                        return context.equals(options.params[0]);
                    }
                });

                handlebars.registerHelper("ifCond", new Helper<Object>() {
                    @Override
                    public Object apply(Object context, Options options) throws IOException {
                        // Verifica si el context o los parámetros son nulos
                        if (context == null || options.params.length < 1) {
                            return options.inverse(null); // Renderiza la parte "else" si context es null
                        }

                        String tipo = (String) options.param(0);
                        // Comparación asegurando que ambos no sean nulos
                        return context.equals(tipo) ? options.fn(context) : options.inverse(context);
                    }
                });

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
        };
    }
}