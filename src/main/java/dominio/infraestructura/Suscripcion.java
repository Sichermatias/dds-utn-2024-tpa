package dominio.infraestructura;

import dominio.persona.Colaborador;
import dominio.services.messageSender.Mensaje;
import dominio.services.messageSender.Mensajero;
import dominio.services.messageSender.strategies.EstrategiaMensaje;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Suscripcion {
    Colaborador colaborador;
    @Getter@Setter
    Heladera heladera;
    @Getter@Setter
    FiltroSuscripcion filtro;
    @Getter@Setter
    Mensajero mensajero;
    @Getter@Setter
    String contacto;
    public Suscripcion(Colaborador colaborador, Heladera heladera, FiltroSuscripcion filtro, Mensajero mensajero, String contacto) {
        this.colaborador = colaborador;
        this.heladera = heladera;
        this.filtro = filtro;
        this.mensajero = mensajero;
        this.contacto = contacto;
    }
    public void notificarColaborador(){
        Mensaje mensaje= new Mensaje(contacto, generarMensaje());
        mensajero.enviarMensaje(mensaje);
    }
    private String generarMensaje() {
        StringBuilder mensajeBuilder = new StringBuilder("La heladera " + heladera.getId());
        if (filtro.getViandasParaLlenar() != null && heladera.getViandasParaLlenar() <= filtro.getViandasParaLlenar()) {
            mensajeBuilder.append(" está a punto de llenarse.");
        }
        if (filtro.getMinViandas() != null && heladera.getViandasActuales() <= filtro.getMinViandas()) {
            mensajeBuilder.append(" está a punto de vaciarse.");
        }
        if (filtro.getDesperfecto() != null && heladera.getDesperfecto()) {
            mensajeBuilder.append(" sufrió un desperfecto.");
        }
        return mensajeBuilder.toString();
    }
}
