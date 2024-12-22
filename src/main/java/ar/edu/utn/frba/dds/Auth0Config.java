package ar.edu.utn.frba.dds;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Auth0Config {
    public static final String DOMAIN = "dev-x3u68ztkuho6jx8w.us.auth0.com";
    public static final String CLIENT_ID = "Q1e0wKCzMbhNUmGbVqbAeIZZKjZ91Gqj";
    public static final String CLIENT_SECRET = "xegAKJuy-ZSOomSKUThP1hOqXkgLwxFwha05x-gbxLsd3dgIJlaBEmkzDyR2C6Mm";

    public static String getAuthURL() {
        return "https://" + DOMAIN + "/authorize" +
                "?response_type=code" +
                "&client_id=" + CLIENT_ID +
                "&redirect_uri=http://localhost:7000/callback" +
                "&scope=openid%20profile%20email";
    }
}
