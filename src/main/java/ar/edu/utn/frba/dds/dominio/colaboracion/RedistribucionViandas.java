package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "redistribucionViandas")
@Setter
@Getter
public class RedistribucionViandas extends Persistente {
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

    @ManyToMany
    @JoinTable(
            name = "vianda_redistribucion",
            joinColumns = @JoinColumn(name = "redistribucion_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vianda_id", referencedColumnName = "id")
    )
    private List<Vianda> viandas;

    @OneToOne
    @JoinColumn(name = "pedidoDeAperturaEnOrigen_id", referencedColumnName = "id")
    private PedidoDeApertura pedidoDeAperturaEnOrigen;

    @OneToOne
    @JoinColumn(name = "pedidoDeAperturaEnDestino_id", referencedColumnName = "id")
    private PedidoDeApertura pedidoDeAperturaEnDestino;

    public RedistribucionViandas() {
        this.colaboracion = new Colaboracion();
    }
    public Double puntaje(){
        return (double) (this.cantidadViandas*1);
    }
}
