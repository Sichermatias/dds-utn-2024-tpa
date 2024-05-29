package dominio.infraestructura;

import dominio.colaboracion.Vianda;
import dominio.contacto.ubicacion.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class Heladera {
    private String nombre;
    private Ubicacion ubicacion;
    private String direccion;
    private Integer cantMaxViandas;
    public ArrayList<Vianda> viandas;
    private LocalDate fechaPuestaEnMarcha;
    @Setter
    private LocalDate ultimaFechaContadaParaPuntaje;
    @Getter @Setter
    private int mesesSinContarParaElPuntaje;
    @Getter
    private Boolean activo;

    public Heladera() {
        this.viandas = new ArrayList<>();
        this.activo = true;
        this.ultimaFechaContadaParaPuntaje = LocalDate.now();
    }

    public void setActivo(boolean activo) {
        this.actualizarMesesSinContarParaPuntaje();
        this.activo = activo;
    }

    public void actualizarMesesSinContarParaPuntaje() {
        LocalDate fechaActual = LocalDate.now();
        if(this.activo) {
            this.mesesSinContarParaElPuntaje += Period.between(this.ultimaFechaContadaParaPuntaje, fechaActual).toTotalMonths();
        }
        this.ultimaFechaContadaParaPuntaje = fechaActual;
    }
}
