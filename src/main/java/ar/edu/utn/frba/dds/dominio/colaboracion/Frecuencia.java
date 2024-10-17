package ar.edu.utn.frba.dds.dominio.colaboracion;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "frecuencia")
@Setter@Getter
public class Frecuencia {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
