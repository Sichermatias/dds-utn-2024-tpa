package ar.edu.utn.frba.dds.dominio.contacto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "medioDeContacto")
@Setter @Getter
public class MedioDeContacto {

    public MedioDeContacto() {
    }

    public MedioDeContacto(String nombre, String valor) {
        this.nombreDeMedioDeContacto.setNombre(nombre);
        this.valor = valor;
    }

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nombreMedioDeContacto_id", referencedColumnName = "id")
    private NombreDeMedioDeContacto nombreDeMedioDeContacto;

    @Column(name = "ContactoDetalle", columnDefinition = "VARCHAR(50)")
    private String valor;

    public MedioDeContacto(NombreDeMedioDeContacto nombreDeMedioDeContacto, String contacto) {
        this.nombreDeMedioDeContacto=nombreDeMedioDeContacto;
        this.valor=contacto;
    }
}
