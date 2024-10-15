package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.utils.Initializer;
import ar.edu.utn.frba.dds.utils.JavalinRenderer;
import com.github.jknack.handlebars.Handlebars;
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

            // Aplicar middleware de autenticación
            AuthMiddleware.apply(app);
            // Aplicar handlers de aplicación
            AppHandlers.applyHandlers(app);
            // Inicializar rutas
            Router.init(app);

            // Manejo de excepciones
            app.exception(AccessDeniedException.class, (e, ctx) -> {
                ctx.status(403);
                ctx.render("Prohibido.hbs"); // Renderiza la plantilla de acceso denegado
            });

            // Inicializar otros componentes necesarios
            Initializer.init();
        }
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            // Configuración de archivos estáticos
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });

            // Configuración del motor de plantillas Handlebars
            config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
                Handlebars handlebars = new Handlebars();
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
