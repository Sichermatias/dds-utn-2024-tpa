package ar.edu.utn.frba.dds.dominio.services.messageSender.adapters;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;

public interface TelegramAdapter {
    void enviarTelegram(Mensaje mensaje);
}
