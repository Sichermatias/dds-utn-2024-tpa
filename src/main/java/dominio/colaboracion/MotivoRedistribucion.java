package dominio.colaboracion;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "motivoRedistribucion")
public class MotivoRedistribucion {
    @Column(name = "descripcion", columnDefinition = "VARCHAR(255)")
    private String descripcion;
}
