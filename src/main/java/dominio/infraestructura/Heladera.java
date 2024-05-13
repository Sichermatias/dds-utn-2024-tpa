package dominio.infraestructura;

import dominio.colaboracion.Vianda;
import dominio.contacto.Ubicacion;

import java.time.LocalDate;
import java.util.ArrayList;

public class Heladera {
    private String nombre;
    private Ubicacion ubicacion;
    private String direccion;
    private Integer cantMaxViandas;
    private ArrayList<Vianda> viandas= new ArrayList<>();
    private LocalDate fechaPuestaEnMarcha;
    private Boolean activo;
}
