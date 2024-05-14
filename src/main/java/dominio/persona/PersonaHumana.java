package dominio.persona;

import dominio.contacto.MedioDeContacto;
import dominio.contacto.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

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