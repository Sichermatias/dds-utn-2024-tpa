package dominio.colaboracion;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "frecuencia")
public class Frecuencia {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
