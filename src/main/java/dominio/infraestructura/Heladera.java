package dominio.infraestructura;

import dominio.contacto.ubicacion.Ubicacion;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
// TODO: 6/28/2024 EN METODOS QUE MODIFIQUEN EL ESTADO(CANTIDAD DE VIANDAS O SET DESPERFECTO EN TRUE)NOTIFICAR A SUSCRIPTORES  
import lombok.Getter;
import lombok.Setter;
@Setter @Getter
public class Heladera {

    private Double id;
    private String nombre;
    private Ubicacion ubicacion;
    private String direccion;
    private Integer cantMaxViandas;
    public Integer viandasActuales;
    public Boolean desperfecto;
    private LocalDate fechaPuestaEnMarcha;
    private LocalDate ultimaFechaContadaParaPuntaje;
    private int mesesSinContarParaElPuntaje;
    private Boolean activo;
    private List<Suscripcion> suscripciones;
    private Modelo modelo;
    private int cantSemanalIncidentes;
    private int cantSemanalViandasRetiradas;
    private int cantSemanalViandasColocadas;

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
