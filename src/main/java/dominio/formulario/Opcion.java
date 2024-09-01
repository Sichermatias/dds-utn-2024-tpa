package dominio.formulario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "opcion")
public class Opcion {
    @Column(name = "textoOpcion", columnDefinition = "TEXT")
    private String textoOpcion;

    @Column(name = "codigo", columnDefinition = "TINYINT")
    private Integer nroOpcion;
}
