package dominio.persona.login;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Rol {
    @Setter
    private String nombreRol;
    private List<Permiso> permisos;

    public Rol() {
        this.permisos = new ArrayList<>();
    }

    public boolean tenesPermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }
}
