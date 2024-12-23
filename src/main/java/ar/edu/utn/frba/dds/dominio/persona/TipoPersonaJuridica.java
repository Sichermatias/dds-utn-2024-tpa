package ar.edu.utn.frba.dds.dominio.persona;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tipoPersonaJuridica")
public class TipoPersonaJuridica {
    @Id
    @GeneratedValue
    private Long id;

    @Getter @Setter
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
