package dominio.persona.login;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Permiso")
@Data
public class Permiso {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "descripcionPermiso", columnDefinition = "VARCHAR(100)")
    private String descripcion;

    public Permiso(String descripcion) {
        this.descripcion = descripcion;
    }
}
