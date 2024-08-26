package dominio.colaboracion;

import dominio.contacto.ubicacion.Ubicacion;
import dominio.persona.Colaborador;
import dominio.persona.PersonaVulnerable;
import dominio.persona.TipoDocumento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class RegistrarPersonasVulnerables {
    @Setter @Getter
    private Colaboracion colaboracion;
    private Colaborador colaborador;
    private PersonaVulnerable personaVulnerable;
    private static Double factorDePuntaje = 2.0;
    private boolean cuentaParaPuntaje;
    public void cargarDatos (String nombre, LocalDate fechaNacimiento, LocalDate fechaRegistro,
                             Ubicacion ubicacion, TipoDocumento tipoDocumento, Integer nroDocumento,
                             String codigoTarjeta, List<PersonaVulnerable> personasVulnACargo){
        this.personaVulnerable = new PersonaVulnerable(nombre, fechaNacimiento, fechaRegistro,
                ubicacion, tipoDocumento, nroDocumento, codigoTarjeta, personasVulnACargo);
    }
    public RegistrarPersonasVulnerables() {
        this.colaboracion = new Colaboracion();
        factorDePuntaje = 2.0;
        this.cuentaParaPuntaje = true;
    }
    public Double puntaje() {
        Double resultado;
        if(cuentaParaPuntaje) {
            resultado = factorDePuntaje;
            this.cuentaParaPuntaje = false;
        } else {
            resultado = 0.0;
        }
        return resultado;
    }
}
