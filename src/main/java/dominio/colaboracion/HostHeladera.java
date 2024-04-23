package dominio.colaboracion;

import dominio.contacto.Ubicacion;
import dominio.infraestructura.Heladera;

import java.time.LocalDate;

//Este sería para las personas juridicas que se hacen cargo de una heladera, no lo tenemos en el diagrama de clases y no recuerdo por que, si no va borrar la clase.
public class HostHeladera extends Colaboracion{
    private Heladera heladeraHosteada;
    private Ubicacion ubicacionHeladera;
    private LocalDate fechaInicio;
}
