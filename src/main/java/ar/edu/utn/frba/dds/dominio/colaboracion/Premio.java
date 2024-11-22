package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "premio")
@Getter @Setter
public class Premio extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "rubro_id", referencedColumnName = "id")
    private RubroPremio rubro;

    @Column(name = "imagenPremio")
    private String imagenPremio;

    @Column(name = "cantidadPuntosNecesarios")
    private Double cantidadPuntosNecesarios;

    @Column(name = "cantidadDisponible")
    private Integer cantidadDisponible;

    public void restarleAlStock(int cantidad) {
        this.cantidadDisponible -= cantidad;
    }
}
