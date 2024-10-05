package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hostearHeladera")
@Getter @Setter
public class HostearHeladera extends Persistente {
    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @OneToOne
    @JoinColumn(name = "colaboracion_id")
    private Colaboracion colaboracion;

    @Column(name = "enVigencia", columnDefinition = "BIT(1)")
    private Boolean enVigencia;

    public HostearHeladera() {
        this.colaboracion = new Colaboracion();
    }
}
