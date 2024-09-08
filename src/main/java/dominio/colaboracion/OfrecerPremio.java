package dominio.colaboracion;

import javax.persistence.*;

@Entity
@Table(name = "ofrecerPremio")
public class OfrecerPremio{
    @ManyToOne
    @JoinColumn(name = "premioCatalogo_id")
    private PremioCatalogo premioCatalogo;

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
