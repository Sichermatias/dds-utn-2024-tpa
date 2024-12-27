package ar.edu.utn.frba.dds.config;

import java.util.Optional;

public class Auth0Config {
    public static final String DOMAIN = Optional.ofNullable(System.getenv("AUTH0_DOMAIN"))
            .orElse("dev-x3u68ztkuho6jx8w.us.auth0.com");
    public static final String CLIENT_ID = Optional.ofNullable(System.getenv("AUTH0_CLIENT_ID"))
            .orElse("Q1e0wKCzMbhNUmGbVqbAeIZZKjZ91Gqj");
    public static final String CLIENT_SECRET = Optional.ofNullable(System.getenv("AUTH0_CLIENT_SECRET"))
            .orElse("xegAKJuy-ZSOomSKUThP1hOqXkgLwxFwha05x-gbxLsd3dgIJlaBEmkzDyR2C6Mm");
    public static final String REDIRECT_URI = Optional.ofNullable(System.getenv("REDIRECT_URI"))
            .orElse("http://localhost:7000/callback");

    public static String getAuthURL() {
        return "https://" + DOMAIN + "/authorize" +
                "?response_type=code" +
                "&client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=openid%20profile%20email";
    }
}
