package dominio.services.messageSender.adapters;


import dominio.services.messageSender.Mensaje;

public interface WhatsappAdapter {

    void enviarWhatsapp(Mensaje mensaje);
}
