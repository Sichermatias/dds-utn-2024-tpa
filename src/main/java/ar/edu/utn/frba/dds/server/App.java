package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.service.AuthService;
import ar.edu.utn.frba.dds.config.Auth0Config;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class App {
    private static final AuthService authService = new AuthService();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.get("/", ctx -> ctx.result("Welcome to Javalin with Auth0!"));

        app.get("/login", ctx -> ctx.redirect(Auth0Config.getAuthURL()));

        app.get("/callback", authService::handleCallback);

        app.get("/dashboard", App::handleDashboard);

        app.get("/logout", authService::handleLogout);
    }

    private static void handleDashboard(Context ctx) {
        if (authService.isAuthenticated(ctx)) {
            ctx.result("Welcome to the protected dashboard!");
        } else {
            ctx.redirect("/login");
        }
    }
}
