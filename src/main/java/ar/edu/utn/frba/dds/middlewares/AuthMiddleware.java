package ar.edu.utn.frba.dds.middlewares;

import ar.edu.utn.frba.dds.models.entities.Rol;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import io.javalin.http.Context;

import java.util.ArrayList;

public class AuthMiddleware {
    public static void apply(Javalin app) {
        ArrayList<String> rutasPublicas= new ArrayList<>();
        rutasPublicas.add("/css/StyleLandingPage.css");
        rutasPublicas.add("/css/StyleLogin.css");
        rutasPublicas.add("/css/StyleRegistroHumana.css");
        rutasPublicas.add("/css/StyleRegistroJuridica.css");
        rutasPublicas.add("/css/StyleSignin.css");
        rutasPublicas.add("/js/*");
        rutasPublicas.add("/check-username");


        ArrayList<String> rutasPermitidas = new ArrayList<>();
        rutasPermitidas.add("/");
        rutasPermitidas.add("/login");
        rutasPermitidas.add("/registro");
        rutasPermitidas.add("/registro/juridica");
        rutasPermitidas.add("/registro/humana");


        app.beforeMatched(ctx -> {
            var userRole = getUserRoleType(ctx);
            if (!isRoleAllowed(userRole,ctx) && !rutasPermitidas.contains(ctx.path()) && ctx.path().startsWith("/public") && ctx.path().startsWith("/css") && ctx.path().startsWith("/js")) {
                throw new AccessDeniedException();
            }
        });
    }

    private static Rol getUserRoleType(Context context) {
        return context.sessionAttribute("tipo_rol") != null?
                Rol.valueOf(context.sessionAttribute("tipo_rol")) : null;
    }

    private static boolean isRoleAllowed(Rol userRole, Context ctx) {
        if (userRole == Rol.ADMIN || userRole == Rol.COLABORADOR_HUMANO || userRole == Rol.COLABORADOR_JURIDICO) {
            return true;
        }else return false;
    }
}
