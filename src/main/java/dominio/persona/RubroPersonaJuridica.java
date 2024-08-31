package dominio.persona;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rubroPersonaJuridica")
public class RubroPersonaJuridica {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
