package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;

import javax.persistence.*;

@Entity
@Table(name = "ofrecerPremio")
public class OfrecerPremio extends Persistente {
    @ManyToOne
    @JoinColumn(name = "premioCatalogo_id")
    private Premio premio;

    @OneToOne
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
