package ar.edu.utn.frba.dds.dominio.colaboracion;


import javax.persistence.*;

@Entity
@Table(name = "motivoRedistribucion")
public class MotivoRedistribucion {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(255)")
    private String descripcion;
}
