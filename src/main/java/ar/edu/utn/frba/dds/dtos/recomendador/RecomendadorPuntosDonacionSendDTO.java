// PuntoGeografico.java
package ar.edu.utn.frba.dds.dtos.recomendador;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecomendadorPuntosDonacionSendDTO {
    private String latitud;
    private String longitud;
    private String radio;
    private String horario;
    private String dias;

    public RecomendadorPuntosDonacionSendDTO(String latitud, String longitud, String radio, String horario, String dias) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.radio = radio;
        this.horario = horario;
        this.dias = dias;
    }
}