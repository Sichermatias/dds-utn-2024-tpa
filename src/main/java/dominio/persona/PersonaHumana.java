package dominio.persona;

import dominio.contacto.Ubicacion;
import lombok.Setter;
import lombok.Getter;
import java.time.LocalDate;
@Setter @Getter
public class PersonaHumana extends Colaborador{
    private TipoDocumento tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String direccion;
    private Ubicacion ubicacion;
    }
}