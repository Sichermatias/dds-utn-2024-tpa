package dominio.contacto.ubicacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "provincia")
public class Provincia {
    @Column(name = "nombreProvincia", columnDefinition = "VARCHAR(100)")
    private String nombreProvincia;
}
