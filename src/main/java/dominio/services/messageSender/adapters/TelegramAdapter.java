package dominio.services.messageSender.adapters;

import dominio.services.messageSender.Mensaje;

public interface TelegramAdapter {
    void enviarTelegram(Mensaje mensaje);
}
