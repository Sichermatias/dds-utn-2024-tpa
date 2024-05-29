package dominio.services.messageSender.adapters;

import dominio.services.messageSender.Mensaje;

public interface MailAdapter {
    void enviarMail(Mensaje mensaje);
}
