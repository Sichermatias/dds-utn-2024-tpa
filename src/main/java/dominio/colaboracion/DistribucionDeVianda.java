package dominio.colaboracion;

import dominio.infraestructura.Heladera;

import java.time.LocalDate;

public class DistribucionDeVianda extends Colaboracion{
    private String nombreDistribucion;
    private Heladera heladeraOrigen;
    private  Heladera heladeraDestino;
    private Integer cantViandas;
    private String motivo;
    private LocalDate fecha;
}
