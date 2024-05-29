package dominio.infraestructura;

import dominio.colaboracion.Vianda;
import dominio.contacto.ubicacion.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
        if (!activo) {
            this.sumarMesesSinContarParaPuntaje();
        }
        this.ultimaFechaContadaParaPuntaje = LocalDate.now();
    }

    private void sumarMesesSinContarParaPuntaje() {
        this.mesesSinContarParaElPuntaje = LocalDate.now().compareTo(this.ultimaFechaContadaParaPuntaje);
    }
}
