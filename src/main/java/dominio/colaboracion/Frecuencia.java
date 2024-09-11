package dominio.colaboracion;


import javax.persistence.*;

@Entity
@Table(name = "frecuencia")
public class Frecuencia {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
