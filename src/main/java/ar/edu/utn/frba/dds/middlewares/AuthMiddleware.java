package ar.edu.utn.frba.dds.middlewares;

import ar.edu.utn.frba.dds.models.entities.Rol;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import io.javalin.http.Context;

import java.util.ArrayList;

public class AuthMiddleware {
    /*public static void apply(JavalinConfig config) {
        ArrayList<String> rutasPermitidas = new ArrayList<>();
        rutasPermitidas.add("/");
        rutasPermitidas.add("/login");
        rutasPermitidas.add("/registro");
        rutasPermitidas.add("/registro/juridica");
        config.accessManager((handler, context, routeRoles) -> {
         if(context.sessionAttribute("usuario_id")==null && !rutasPermitidas.contains(context.path())){
             throw new AccessDeniedException();
         }
         else
         {handler.handle(context);}
        });
    }*/

    public static void apply(Javalin app) {
        app.beforeMatched(ctx -> {
            var userRole = getUserRoleType(ctx);
            if (!ctx.routeRoles().isEmpty() && !ctx.routeRoles().contains(userRole)) {
                throw new AccessDeniedException();
            }
        });
    }

    private static Rol getUserRoleType(Context context) {
        return context.sessionAttribute("tipo_rol") != null?
                Rol.valueOf(context.sessionAttribute("tipo_rol")) : null;
    }
}
