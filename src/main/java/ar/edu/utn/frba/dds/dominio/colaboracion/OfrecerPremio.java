package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ofrecerPremio")
@Setter @Getter
public class OfrecerPremio extends Persistente {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "premio_id")
    private Premio premio;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaboracion_id")
    private Colaboracion colaboracion;

    @Column(name = "cantidad", columnDefinition = "INTEGER(7)")
    private Integer cantidad;

    public OfrecerPremio() {
        this.colaboracion = new Colaboracion();
    }
    public Double puntaje() {
        return 0.0;
    }
}
