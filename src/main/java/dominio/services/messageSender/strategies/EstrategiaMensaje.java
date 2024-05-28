package dominio.services.messageSender.strategies;

import dominio.services.messageSender.Mensaje;

public interface EstrategiaMensaje {
    void enviarMensaje(Mensaje mensaje);
}
