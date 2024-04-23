package dominio.persona;

import dominio.colaboracion.Colaboracion;
import dominio.contacto.MedioDeContacto;
import dominio.formulario.FormularioRespondido;

import java.util.ArrayList;

abstract class Colaborador {
    private ArrayList<Colaboracion> colaboraciones= new ArrayList<>();
    private ArrayList<MedioDeContacto> mediosDeContacto= new ArrayList<>();
    private FormularioRespondido formularioRespondido;
}
