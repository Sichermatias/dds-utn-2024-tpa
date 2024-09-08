package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "redistribucionViandas")
@Setter
@Getter
public class RedistribucionViandas {
    @ManyToOne
    @JoinColumn(name = "heladeraOrigen_id", referencedColumnName = "id")
    private Heladera heladeraOrigen;

    @ManyToOne
    @JoinColumn(name = "heladeraDestino_id", referencedColumnName = "id")
    private Heladera heladeraDestino;

    @Column(name = "cantidadViandas", columnDefinition = "INTEGER(6)")
    private Integer cantidadViandas;

    @ManyToOne
    @JoinColumn(name = "motivoRedistribucion_id", referencedColumnName = "id")
    private MotivoRedistribucion motivoRedistribucion;

    @OneToOne
    @JoinColumn(name = "colaboracion_id")
    private Colaboracion colaboracion;

    @OneToOne
    @JoinColumn(name = "pedidoDeAperturaEnOrigen_id", referencedColumnName = "id")
    private PedidoDeApertura pedidoDeAperturaEnOrigen;

    @OneToOne
    @JoinColumn(name = "pedidoDeAperturaEnDestino_id", referencedColumnName = "id")
    private PedidoDeApertura pedidoDeAperturaEnDestino;

    public RedistribucionViandas() {
        this.colaboracion = new Colaboracion();
    }
}
