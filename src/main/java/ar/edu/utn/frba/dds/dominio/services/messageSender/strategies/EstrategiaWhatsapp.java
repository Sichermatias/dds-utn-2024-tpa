package ar.edu.utn.frba.dds.dominio.services.messageSender.strategies;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.WhatsappAdapter;

public class EstrategiaWhatsapp implements EstrategiaMensaje {
    private WhatsappAdapter whatsappAdapter;

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        whatsappAdapter.enviarWhatsapp(mensaje);
    }
}
