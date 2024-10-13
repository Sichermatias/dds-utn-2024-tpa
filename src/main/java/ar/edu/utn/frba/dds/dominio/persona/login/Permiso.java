package ar.edu.utn.frba.dds.dominio.persona.login;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Permiso")
@Data
public class Permiso {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
    private String nombre;

    @Column(name = "nombreInterno", columnDefinition = "VARCHAR(100)")
    private String nombreInterno;

    public Permiso(String nombre) {
        this.nombre = nombre;
    }

    public boolean coincideConNombreInterno(String nombre) {
        return this.nombreInterno.equals(nombre);
    }
}
