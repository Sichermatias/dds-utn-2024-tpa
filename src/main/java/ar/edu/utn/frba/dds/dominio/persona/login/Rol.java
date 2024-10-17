package ar.edu.utn.frba.dds.dominio.persona.login;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rol")
@Getter
public class Rol {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "rolNombre", columnDefinition = "VARCHAR(50)")
    @Setter
    private String nombreRol;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoRol tipo;

    @ManyToMany
    @JoinTable(name = "rol_permiso",
                joinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id"))
    private List<Permiso> permisos;

    public Rol() {
        this.permisos = new ArrayList<>();
    }

    public boolean tienePermiso(String nombreInternoPermiso) {
        return this.permisos.stream().anyMatch(p -> p.coincideConNombreInterno(nombreInternoPermiso));
    }

    public String nombreTipoRol() {
        return this.tipo.toString();
    }
}
