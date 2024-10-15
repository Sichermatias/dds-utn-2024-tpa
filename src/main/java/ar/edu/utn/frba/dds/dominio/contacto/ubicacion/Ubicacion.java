package ar.edu.utn.frba.dds.dominio.contacto.ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Setter@Getter
public class Ubicacion {
    @Column(name = "direccion", columnDefinition = "VARCHAR(255)")
    private String direccion;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;
}
