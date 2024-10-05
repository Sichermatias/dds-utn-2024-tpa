package ar.edu.utn.frba.dds.dominio.services.messageSender.strategies;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.TelegramAdapter;

public class EstrategiaTelegram implements EstrategiaMensaje {
    private TelegramAdapter telegramAdapter;

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        telegramAdapter.enviarTelegram(mensaje);
    }
}
