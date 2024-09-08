package dominio.colaboracion;

import javax.persistence.*;

@Entity
@Table(name = "premioCatalogo")
public class PremioCatalogo {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "rubro_id", referencedColumnName = "id")
    private RubroPremio rubro;

    @Column(name = "imagenPremio")
    private String imagenPremio;

    @Column(name = "cantidadPuntosNecesarios")
    private Integer cantidadPuntosNecesarios;

    @Column(name = "cantidadDisponible")
    private Integer cantidadDisponible;
}
