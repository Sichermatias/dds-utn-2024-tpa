package dominio.services.messageSender.strategies;

import dominio.services.messageSender.Mensaje;
import dominio.services.messageSender.adapters.MailAdapter;

public class EstrategiaMail implements EstrategiaMensaje {
    private MailAdapter mailAdapter;

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        mailAdapter.enviarMail(mensaje);
    }
}
