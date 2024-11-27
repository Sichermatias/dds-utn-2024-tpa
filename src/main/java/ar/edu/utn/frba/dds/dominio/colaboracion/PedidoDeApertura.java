package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Tarjeta;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "pedidoDeApertura")
public class PedidoDeApertura extends Persistente {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @ManyToOne
    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    private Tarjeta tarjeta;

    @Column(name = "fechaHoraRealizada")
    private LocalDateTime fechaHoraRealizada;

    @Column(name = "valido", columnDefinition = "BIT(1)")
    private Boolean valido=true;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "coloboracionCantidadViandas", columnDefinition = "INTEGER(5)")
    private Integer cantidadViandas;

    public PedidoDeApertura() {
    }
}
