package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "incidente")
@Setter @Getter
public class Incidente extends Persistente {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "heladera_id")
    private Heladera heladeraIncidente;

    @Enumerated(EnumType.STRING)
    private TipoIncidente tipoIncidente;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaboradorIncidente;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(255)")
    private String descripcionIncidente;

    @Column(name = "foto", columnDefinition = "VARCHAR(255)")
    private String fotoIncidente;

    @Column(name = "asignado", columnDefinition = "BIT(1)")
    private Boolean asignado=false;
    //TODO A DIAGRAMA
    @Column(name="resuelto", columnDefinition ="BIT(1)")
    private Boolean resuelto=false;

    @Column(name = "fechaHoraResuelto", columnDefinition = "DATETIME")
    private LocalDateTime fechaHoraResuelto;

    public Incidente(LocalDateTime fechaIncidente, Heladera heladeraIncidente, TipoIncidente tipoIncidente, Colaborador colaboradorIncidente, String descripcionIncidente, String fotoIncidente) {
        this.fechaHoraAlta = fechaIncidente;
        this.heladeraIncidente = heladeraIncidente;
        this.tipoIncidente = tipoIncidente;
        this.colaboradorIncidente = colaboradorIncidente;
        this.descripcionIncidente = descripcionIncidente;
        this.fotoIncidente = fotoIncidente;
    }

    public Incidente() {

    }
}
