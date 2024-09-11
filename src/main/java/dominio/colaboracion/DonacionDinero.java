package dominio.colaboracion;

import dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "donacionDinero")
@Setter @Getter
public class DonacionDinero extends Persistente {
    @Column(name = "monto")
    private Double monto;

    @ManyToOne
    @JoinColumn(name = "frecuencia_id", referencedColumnName = "id")
    private Frecuencia frecuencia;

    @OneToOne
    @JoinColumn(name = "colaboracion_id")
    private Colaboracion colaboracion;

    public DonacionDinero() {
        this.colaboracion = new Colaboracion();
    }
}
