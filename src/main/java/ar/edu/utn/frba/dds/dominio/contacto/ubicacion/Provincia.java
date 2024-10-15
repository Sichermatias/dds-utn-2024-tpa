package ar.edu.utn.frba.dds.dominio.contacto.ubicacion;

import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    @Column(name = "nombreProvincia", columnDefinition = "VARCHAR(100)")
    private String nombreProvincia;
}
