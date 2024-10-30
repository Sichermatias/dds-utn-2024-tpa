// RecomendadorPuntosDonacionDTO.java
package ar.edu.utn.frba.dds.dtos.recomendador;

import java.util.List;

import ar.edu.utn.frba.dds.dominio.calculadorDePuntos.PuntoRecomendado;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecomendadorPuntosDonacionReceiveDTO {
    private boolean activo;
    private String nombre;
    private PuntoRecomendado puntoGeografico;
    private List<Integer> horaApertura;
    private List<Integer> horaCierre;
    private List<String> diasAbierto;
    private int id;

}