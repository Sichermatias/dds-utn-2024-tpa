package dominio.persona;

import dominio.colaboracion.Colaboracion;
import dominio.contacto.MedioDeContacto;
import dominio.formulario.FormularioRespondido;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Setter @Getter
abstract class Colaborador {
    private ArrayList<Colaboracion> colaboraciones= new ArrayList<>();
    private ArrayList<MedioDeContacto> mediosDeContacto= new ArrayList<>();
    private FormularioRespondido formularioRespondido;

    public void agregarMedioContacto(MedioDeContacto contacto){
        mediosDeContacto.add(contacto);
    }
}
