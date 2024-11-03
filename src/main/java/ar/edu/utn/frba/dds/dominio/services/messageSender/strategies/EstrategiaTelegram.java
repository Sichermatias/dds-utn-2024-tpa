package ar.edu.utn.frba.dds.dominio.services.messageSender.strategies;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.TelegramAdapter;
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

public class EstrategiaTelegram implements EstrategiaMensaje {
    private TelegramAdapter telegramAdapter;
    @Override
    public void enviarMensaje(Mensaje mensaje) {
        telegramAdapter.enviarTelegram(mensaje);
    }
}
