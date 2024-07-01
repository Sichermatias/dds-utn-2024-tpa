package dominio.contacto.ubicacion;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Localidad {
    private Provincia provincia;
    private String nombreLocalidad;
}
