package ar.edu.utn.frba.dds.dominio.services.messageSender.strategies;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;

public interface EstrategiaMensaje {
    void enviarMensaje(Mensaje mensaje);
}
