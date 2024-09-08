package dominio.colaboracion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rubroPremio")
public class RubroPremio {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
