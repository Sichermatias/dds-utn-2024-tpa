package ar.edu.utn.frba.dds.dominio.colaboracion;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "motivoRedistribucion")
public class MotivoRedistribucion {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(255)")
    @Getter
    private String descripcion;

    public MotivoRedistribucion(String motivoRedistribucion){
        this.descripcion=motivoRedistribucion;
    }

    public MotivoRedistribucion(){
    }
}
