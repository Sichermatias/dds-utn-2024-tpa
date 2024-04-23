package dominio.persona;

import dominio.contacto.Ubicacion;
import dominio.formulario.FormularioRespondido;

import java.time.LocalDate;

public class PersonaHumana extends Colaborador{
    //private FormularioRespondido formularioRespondido; Lo tengo en colaborador.
    //private ArrayList<Atributo> Atributos; Figura en el diagrama, no se a que se refiere.
    private String nombre;
    private String apellido;
    //private ArrayList<MedioDeContacto> mediosDeContacto; Tambien en colaborador.
    private LocalDate fechaNac;
    private String direccion;
    private Ubicacion ubicacion;
}