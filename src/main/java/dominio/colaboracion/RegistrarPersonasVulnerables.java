package dominio.colaboracion;

import dominio.contacto.ubicacion.Ubicacion;
import dominio.persona.Persona;
import dominio.persona.PersonaVulnerable;
import dominio.persona.TipoDocumento;

import java.time.LocalDate;
import java.util.List;

public class RegistrarPersonasVulnerables {
    private Persona colaborador;
    private PersonaVulnerable personaVulnerable;

    public void cargarDatos (String nombre, LocalDate fechaNacimiento, LocalDate fechaRegistro,
                             Ubicacion ubicacion, TipoDocumento tipoDocumento, Integer nroDocumento,
                             String codigoTarjeta, List<PersonaVulnerable> personasVulnACargo){
        this.personaVulnerable = new PersonaVulnerable(nombre, fechaNacimiento, fechaRegistro,
                ubicacion, tipoDocumento, nroDocumento, codigoTarjeta, personasVulnACargo);
    }
}
