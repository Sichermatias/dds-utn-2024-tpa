package dominio.colaboracion;

import dominio.infraestructura.Heladera;

import java.time.LocalDate;

public class Vianda extends TipoColaboracion{
    private String nombreComida;
    private LocalDate fechaCaducidad;
    private LocalDate fechaDonacion;
    private Heladera heladeraAsignada;
    private Double calorias;
    private Double peso;
    private Boolean fueEntregado;
}
