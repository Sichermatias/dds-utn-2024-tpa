package dominio.infraestructura;

import dominio.Persistente;
import dominio.contacto.ubicacion.Ubicacion;


import java.time.LocalDate;
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
public class Heladera extends Persistente {

    @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
    private String nombre;

    @Embedded
    private Ubicacion ubicacion;

    @Column(name = "cantMaxViandas", columnDefinition = "INTEGER(5)")
    private Integer cantMaxViandas;

    @Column(name = "cantViandasActuales", columnDefinition = "INTEGER(5)")
    public Integer cantViandasActuales;

    @Column(name = "fechaPuestaEnMarcha", columnDefinition = "DATE")
    private LocalDate fechaPuestaEnMarcha;

    @Column(name = "ultimaFechaContadaParaPuntaje", columnDefinition = "DATE")
    private LocalDate ultimaFechaContadaParaPuntaje;

    @Column(name = "desperfecto", columnDefinition = "BIT(1)")
    public Boolean desperfecto;

    //TODO: cómo se mapea esto?
    @Transient
    private List<Suscripcion> suscripciones;

    @ManyToOne
    @JoinColumn(name = "modelo_id", referencedColumnName = "id")
    private Modelo modelo;

    @Column(name = "cantSemanalIncidentes", columnDefinition = "TINYINT")
    private int cantSemanalIncidentes;

    @Column(name = "cantSemanalViandasRetiradas", columnDefinition = "INTEGER(5)")
    private int cantSemanalViandasRetiradas;

    @Column(name = "cantSemanalViandasColocadas", columnDefinition = "INTEGER(5)")
    private int cantSemanalViandasColocadas;

    public Heladera() {
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
        return cantMaxViandas - cantViandasActuales;
    }
}