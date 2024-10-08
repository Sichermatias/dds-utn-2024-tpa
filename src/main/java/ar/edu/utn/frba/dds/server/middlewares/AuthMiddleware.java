package ar.edu.utn.frba.dds.server.middlewares;

import io.javalin.config.JavalinConfig;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;

import java.util.ArrayList;

public class AuthMiddleware {
    public static void apply(JavalinConfig config) {
        ArrayList<String> rutasPermitidas = new ArrayList<>();
        rutasPermitidas.add("/");
        rutasPermitidas.add("/login");
        rutasPermitidas.add("/registro");
        config.accessManager((handler, context, routeRoles) -> {
         if(context.sessionAttribute("usuario_id")==null && !rutasPermitidas.contains(context.path())){
             throw new AccessDeniedException();
         }
         else
         {handler.handle(context);}
        });
    }
}
