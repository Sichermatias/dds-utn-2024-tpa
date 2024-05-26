package dominio.persona.login;

import lombok.Data;

@Data
public class Permiso {
    private String descripcion;

    public Permiso(String descripcion) {
        this.descripcion = descripcion;
    }
}
