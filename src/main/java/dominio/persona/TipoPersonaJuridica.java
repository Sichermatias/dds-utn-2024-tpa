package dominio.persona;

import javax.persistence.*;

@Entity
@Table(name = "tipoPersonaJuridica")
public class TipoPersonaJuridica {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
