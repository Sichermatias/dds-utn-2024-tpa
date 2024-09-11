package dominio.persona;

import javax.persistence.*;

@Entity
@Table(name = "rubroPersonaJuridica")
public class RubroPersonaJuridica {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
