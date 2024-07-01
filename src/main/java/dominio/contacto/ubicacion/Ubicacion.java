package dominio.contacto.ubicacion;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class Ubicacion {
    private String direccion;
    private Localidad localizacion;
    private Double latitud;
    private Double longitud;
}
