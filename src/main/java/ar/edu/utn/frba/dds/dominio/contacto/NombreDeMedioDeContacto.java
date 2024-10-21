package ar.edu.utn.frba.dds.dominio.contacto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "nombreMedioDeContacto")
@Setter @Getter
public class NombreDeMedioDeContacto {

    public NombreDeMedioDeContacto() {
    }


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
    private String nombre;

    public NombreDeMedioDeContacto(String nombreContacto) {
        this.nombre=nombreContacto;
    }
}
