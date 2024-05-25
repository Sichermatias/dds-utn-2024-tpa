package dominio.persona;

import dominio.colaboracion.Colaboracion;
import dominio.contacto.MedioDeContacto;
import dominio.contacto.Ubicacion;
import dominio.formulario.FormularioRespondido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class Persona {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private TipoDocumento tipoDocumento;
    private Integer nroDocumento;
    private String razonSocial;
    private RubroPersonaJuridica rubroPersonaJuridica;
    private TipoPersonaJuridica tipoPersonaJuridica;
    private TipoPersona tipoPersona;
    private FormularioRespondido formularioRespondido;
    private List<MedioDeContacto> mediosDeContacto;
    private Ubicacion ubicacion;
    private List<Colaboracion> colaboraciones;
    private CalculadorDePuntosAcumulados calculadorDePuntos;

    public void agregarMediosDeContacto(MedioDeContacto ... mediosDeContacto) {
        Collections.addAll(this.mediosDeContacto, mediosDeContacto);
    }

    public void agregarColaboraciones(Colaboracion ... colaboraciones) {
        Collections.addAll(this.colaboraciones, colaboraciones);
    }
}
