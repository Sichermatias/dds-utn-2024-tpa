package ar.edu.utn.frba.dds.dominio.services.messageSender.strategies;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.MailAdapter;

public class EstrategiaMail implements EstrategiaMensaje {
    private MailAdapter mailAdapter;

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        mailAdapter.enviarMail(mensaje);
    }
}
