package ar.edu.utn.frba.dds.dominio.contacto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "medioDeContacto")
@Setter @Getter
public class MedioDeContacto {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "nombreMedioDeContacto_id", referencedColumnName = "id")
    private NombreDeMedioDeContacto nombreDeMedioDeContacto;

    @Column(name = "ContactoDetalle", columnDefinition = "VARCHAR(50)")
    private String valor;
}
