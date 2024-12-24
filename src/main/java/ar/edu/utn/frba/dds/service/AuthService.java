package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.config.Auth0Config;
import io.javalin.http.Context;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class AuthService {

    private final OkHttpClient client = new OkHttpClient();

    public void handleCallback(Context ctx) {
        String authCode = ctx.queryParam("code");
        if (authCode == null) {
            ctx.status(400).result("Authorization code is missing");
            return;
        }

        try {
            String tokenResponse = exchangeCodeForToken(authCode);
            JSONObject jsonResponse = new JSONObject(tokenResponse);
            String accessToken = jsonResponse.getString("access_token");

            ctx.cookie("auth_token", accessToken);
            ctx.redirect("/dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error processing authentication callback");
        }
    }

    public void handleLogout(Context ctx) {
        ctx.removeCookie("auth_token");

        String logoutUrl = "https://" + Auth0Config.DOMAIN + "/v2/logout" +
                "?returnTo=" + Auth0Config.REDIRECT_URI +
                "&client_id=" + Auth0Config.CLIENT_ID;

        ctx.redirect(logoutUrl);
    }

    public boolean isAuthenticated(Context ctx) {
        String token = ctx.cookie("auth_token");
        return validateToken(token);
    }

    private String exchangeCodeForToken(String authCode) throws IOException {
        String url = "https://" + Auth0Config.DOMAIN + "/oauth/token";
        RequestBody body = new FormBody.Builder()
                .add("client_id", Auth0Config.CLIENT_ID)
                .add("client_secret", Auth0Config.CLIENT_SECRET)
                .add("code", authCode)
                .add("redirect_uri", Auth0Config.REDIRECT_URI)
                .add("grant_type", "authorization_code")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            return response.body().string();
        }
    }

    private boolean validateToken(String token) {
        // Aquí podrías implementar validación real usando una librería JWT
        return token != null && !token.isEmpty();
    }
}
