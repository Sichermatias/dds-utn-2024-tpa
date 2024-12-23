package ar.edu.utn.frba.dds.dominio.persona;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "rubroPersonaJuridica")
public class RubroPersonaJuridica {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    @Setter
    @Getter
    private String nombre;
}
