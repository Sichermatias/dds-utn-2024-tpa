package ar.edu.utn.frba.dds.dominio.services.messageSender.adapters;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelegramSender {

    private static final String BOT_TOKEN = ""; // Coloca aquí el token de tu bot (moverlo a un config)
   // private static final String CHAT_ID = "";   // Coloca aquí el chat ID del usuario o grupo (se asigna abajo)

    public static void enviarMensajeTelegram(Mensaje mensaje){
        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("chat_id", mensaje.getReceptor()));
            params.add(new BasicNameValuePair("text", mensaje.getMensaje()));
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Mensaje enviado correctamente");
                } else {
                    System.err.println("Error al enviar el mensaje: " + response.getStatusLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
