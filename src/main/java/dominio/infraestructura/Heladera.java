package dominio.infraestructura;

import dominio.colaboracion.Vianda;
import dominio.contacto.ubicacion.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
// TODO: 6/28/2024 EN METODOS QUE MODIFIQUEN EL ESTADO(CANTIDAD DE VIANDAS O SET DESPERFECTO EN TRUE)NOTIFICAR A SUSCRIPTORES  

public class Heladera {
    @Getter
    private Double id;
    private String nombre;
    private Ubicacion ubicacion;
    private String direccion;
    private Integer cantMaxViandas;
    @Setter@Getter
    public Integer viandasActuales;
    @Getter
    public Boolean desperfecto;
    private LocalDate fechaPuestaEnMarcha;
    @Setter
    private LocalDate ultimaFechaContadaParaPuntaje;
    @Getter @Setter
    private int mesesSinContarParaElPuntaje;
    @Getter
    private Boolean activo;
    private List<Suscripcion> suscripciones;

    public Heladera() {
        this.activo = true;
        this.ultimaFechaContadaParaPuntaje = LocalDate.now();
        this.suscripciones = new ArrayList<>();
    }
    public void agregarSuscripcion(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }

    public void eliminarSuscripcion(Suscripcion suscripcion) {
        suscripciones.remove(suscripcion);
    }

    public void notificarSuscriptores() {
        for (Suscripcion suscripcion : suscripciones) {
            if (suscripcion.getFiltro().cumpleCondiciones(this)) {
                suscripcion.notificarColaborador();
            }
        }
    }
    public Integer getViandasParaLlenar(){
        return cantMaxViandas - viandasActuales;
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
