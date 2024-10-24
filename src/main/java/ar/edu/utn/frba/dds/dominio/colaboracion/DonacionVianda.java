package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "donacionVianda")
public class DonacionVianda extends Persistente {
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vianda_id", referencedColumnName = "id")
    @Setter@Getter
    private Vianda vianda;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaboracion_id", referencedColumnName = "id")
    @Setter @Getter
    private Colaboracion colaboracion;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pedidoDeApertura_id", referencedColumnName = "id")
    @Setter@Getter
    private PedidoDeApertura pedidoDeApertura;

    @Column(name = "cantViandas")
    @Getter@Setter
    private Double cantViandas;

    public DonacionVianda() {
        this.colaboracion = new Colaboracion();
    }
    public Double puntaje(){
        return this.cantViandas*1.5;
    }
}
