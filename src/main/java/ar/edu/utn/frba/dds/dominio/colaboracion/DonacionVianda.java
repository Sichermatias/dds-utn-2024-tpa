package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "donacionVianda")
public class DonacionVianda extends Persistente {
    //TODO: tal vez deberia ser una lista
    @OneToOne
    @JoinColumn(name = "vianda_id", referencedColumnName = "id")
    private Vianda vianda;

    @OneToOne
    @JoinColumn(name = "colaboracion_id", referencedColumnName = "id")
    @Setter @Getter
    private Colaboracion colaboracion;

    @OneToOne
    @JoinColumn(name = "pedidoDeApertura_id", referencedColumnName = "id")
    private PedidoDeApertura pedidoDeApertura;

    public DonacionVianda() {
        this.colaboracion = new Colaboracion();
    }
}
