package dominio.services.messageSender.strategies;

import dominio.services.messageSender.Mensaje;
import dominio.services.messageSender.adapters.WhatsappAdapter;

public class EstrategiaWhatsapp implements EstrategiaMensaje {
    private WhatsappAdapter whatsappAdapter;

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        whatsappAdapter.enviarWhatsapp(mensaje);
    }
}
