package ar.edu.utn.frba.dds.dominio.contacto.ubicacion;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
@Setter
@Getter
public class Localidad {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @Column(name = "nombreLocalidad", columnDefinition = "VARCHAR(100)")
    private String nombreLocalidad;
}
