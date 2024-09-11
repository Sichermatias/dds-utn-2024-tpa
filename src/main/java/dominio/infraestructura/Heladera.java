package dominio.infraestructura;

import dominio.contacto.ubicacion.Ubicacion;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
// TODO: 6/28/2024 EN METODOS QUE MODIFIQUEN EL ESTADO(CANTIDAD DE VIANDAS O SET DESPERFECTO EN TRUE)NOTIFICAR A SUSCRIPTORES
// TODO 2024-07-03: cuando se colocan o se retiran viandas, hay que sumarle a su respectivo contador
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "heladera")
@Setter @Getter
public class Heladera {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombreHeladera", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Transient
    private Ubicacion ubicacion;

    @Column(name = "direccion", columnDefinition = "VARCHAR(100)")
    private String direccion;

    @Column(name = "cantMaxViandas", columnDefinition = "INTEGER")
    private Integer cantMaxViandas;

    @Column(name = "cantViandasActual", columnDefinition = "INTEGER")
    public Integer viandasActuales;

    @Column(name = "desperfecto", columnDefinition = "BINARY")
    public Boolean desperfecto;

    @Column(name = "fechaPuestaEnMarcha", columnDefinition = "DATE")
    private LocalDate fechaPuestaEnMarcha;

    @Column(name = "ultimaFechaPuntaje", columnDefinition = "DATE")
    private LocalDate ultimaFechaContadaParaPuntaje;

    @Column(name = "cantMesesSinPuntaje", columnDefinition = "INTEGER")
    private int mesesSinContarParaElPuntaje;

    @Column(name = "estado", columnDefinition = "BINARY")
    private Boolean activo;

    @Transient //Bidireccionalidad?
    private List<Suscripcion> suscripciones;

    @Column(name = "modeloHeladera", columnDefinition = "VARCHAR(50)")
    private Modelo modelo;

    @Column(name = "cantSemanalIncidentes", columnDefinition = "INTEGER")
    private int cantSemanalIncidentes;

    @Column(name = "cantSemanalViandasRetiradas", columnDefinition = "INTEGER")
    private int cantSemanalViandasRetiradas;

    @Column(name = "cantSemanalViandasColocadas", columnDefinition = "INTEGER")
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
