package dominio.services.messageSender.strategies;

import dominio.services.messageSender.Mensaje;
import dominio.services.messageSender.adapters.TelegramAdapter;

public class EstrategiaTelegram implements EstrategiaMensaje {
    private TelegramAdapter telegramAdapter;

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        telegramAdapter.enviarTelegram(mensaje);
    }
}
