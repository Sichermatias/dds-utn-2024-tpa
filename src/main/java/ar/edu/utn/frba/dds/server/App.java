package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.Auth0Config;
import io.javalin.Javalin;
import io.javalin.http.Context;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try (Javalin app = Javalin.create().start(7000)) {

            app.get("/", ctx -> ctx.result("Welcome to Javalin with Auth0!"));

            app.get("/login", ctx -> ctx.redirect(Auth0Config.getAuthURL()));

            app.get("/callback", App::handleCallback);

            app.get("/dashboard", ctx -> {
                String token = ctx.cookie("auth_token");
                if (token != null) {
                    ctx.result("Welcome to the protected dashboard!");
                } else {
                    ctx.redirect("/login");
                }
            });

            app.get("/logout", App::handleLogout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleCallback(Context ctx) {
        // Aquí obtienes el código de autorización (el que se devuelve en la URL)
        String authCode = ctx.queryParam("code");

        // Realiza el intercambio del código por un token
        try {
            // La URL de Auth0 para el intercambio de código por token
            String url = "https://" + Auth0Config.DOMAIN + "/oauth/token"; // CORREGIDO

            String redirectUri = "http://localhost:7000/callback"; // Debe ser tu URI de redirección

            // Solicitar el token de Auth0 usando el código
            OkHttpClient client = new OkHttpClient();
            assert authCode != null;
            RequestBody body = new FormBody.Builder()
                    .add("client_id", Auth0Config.CLIENT_ID) // CORREGIDO (tu client_id real)
                    .add("client_secret", Auth0Config.CLIENT_SECRET) // CORREGIDO (tu client_secret real)
                    .add("code", authCode)
                    .add("redirect_uri", redirectUri)
                    .add("grant_type", "authorization_code")
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            // Verifica si la respuesta es exitosa
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Obtiene la respuesta JSON
            String responseBody = response.body().string();

            // Aquí parseamos la respuesta JSON para extraer los tokens
            JSONObject jsonResponse = new JSONObject(responseBody);
            String accessToken = jsonResponse.getString("access_token");
            String idToken = jsonResponse.getString("id_token");

            // Usa el accessToken para realizar la siguiente operación
            System.out.println("Access Token: " + accessToken);
            System.out.println("ID Token: " + idToken);

            // Almacena el access token en una cookie
            ctx.cookie("auth_token", accessToken);

            // Redirige a donde desees
            ctx.redirect("/dashboard");

        } catch (IOException e) {
            e.printStackTrace();
            ctx.status(500).result("Error al procesar el token");
        }
    }

    public static void handleLogout(Context ctx) {
        // Elimina la cookie que contiene el token
        ctx.removeCookie("auth_token");

        // Redirige a Auth0 para cerrar la sesión también en su plataforma
        String redirectUri = "http://localhost:7000"; // Cambia esto por la URL de tu aplicación
        String logoutUrl = "https://" + Auth0Config.DOMAIN + "/v2/logout?returnTo=" + redirectUri + "&client_id=" + Auth0Config.CLIENT_ID;

        // Redirige a la URL de logout de Auth0
        ctx.redirect(logoutUrl);
    }
}
