package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "incidente")
@Setter @Getter
public class Incidente extends Persistente {
    @ManyToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladeraIncidente;

    @Enumerated(EnumType.STRING)
    private TipoIncidente tipoIncidente;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaboradorIncidente;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(255)")
    private String descripcionIncidente;

    @ElementCollection
    @CollectionTable(name = "incidente_foto_id", joinColumns = @JoinColumn(name = "Incidente_id"))
    @Column(name = "foto")
    private List<String> fotosIncidente;

    public Incidente(LocalDateTime fechaIncidente, Heladera heladeraIncidente, TipoIncidente tipoIncidente, Colaborador colaboradorIncidente, String descripcionIncidente, String fotosIncidente) {
        this.fechaHoraAlta = fechaIncidente;
        this.heladeraIncidente = heladeraIncidente;
        this.tipoIncidente = tipoIncidente;
        this.colaboradorIncidente = colaboradorIncidente;
        this.descripcionIncidente = descripcionIncidente;
        this.fotosIncidente = new ArrayList<>();
    }

    public Incidente() {

    }
}
