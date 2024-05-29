package dominio.services.messageSender;


import dominio.services.messageSender.strategies.EstrategiaMensaje;
import lombok.*;
@Getter
@Setter
public class Mensajero {
    private EstrategiaMensaje estrategiaMensaje;

    public Mensajero(EstrategiaMensaje estrategiaMensaje){
        this.estrategiaMensaje = estrategiaMensaje;
    }

    public void enviarMensaje(Mensaje mensaje){
        estrategiaMensaje.enviarMensaje(mensaje);
    }
    public void cambiarEstrategia(EstrategiaMensaje estrategiaMensaje){
        this.estrategiaMensaje = estrategiaMensaje;
    }
}
