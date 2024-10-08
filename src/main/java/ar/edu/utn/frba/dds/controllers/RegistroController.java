package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import ar.edu.utn.frba.dds.dominio.persona.*;
import ar.edu.utn.frba.dds.models.repositories.imp.PersonaHumanaRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class RegistroController implements WithSimplePersistenceUnit {
    private PersonaHumanaRepositorio personaRepositorio;

    public RegistroController(PersonaHumanaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    public void elegirTipo(Context context) {
        Map<String, Object> model = new HashMap<>();
        context.render("Registro.hbs", model);
    }

    public void formularioJuridica(Context context) {
        Map<String, Object> model = new HashMap<>();
        context.render("Registro-Juridica.hbs", model);
    }

    public void formularioHumana(Context context) {
        Map<String, Object> model = new HashMap<>();
        context.render("Registro-Humana.hbs", model);
    }

    public void registrarJuridica(Context context) {

        Colaborador colaborador = new Colaborador();

        colaborador.setRazonSocial(context.formParam("razonSocial"));
        colaborador.setTipoPersona(TipoPersona.JURIDICA);


      //  RubroPersonaJuridica rubro = // Obtener el rubro de algún repositorio si es necesario
                // colaborador.setRubroPersonaJuridica(rubro);

       // TipoPersonaJuridica tipoPersonaJuridica = ;
       // colaborador.setTipoPersonaJuridica(tipoPersonaJuridica);

        NombreDeMedioDeContacto nombreDeMedioDeContacto= new NombreDeMedioDeContacto(context.formParam("nombreContacto"));
        MedioDeContacto medioDeContacto = new MedioDeContacto(nombreDeMedioDeContacto,context.formParam("contacto"));
        colaborador.agregarMedioDeContacto(medioDeContacto);

                personaRepositorio.agregar(colaborador);
        context.status(HttpStatus.CREATED).redirect("/login");


        String razonSocial = context.formParam("razonSocial");
        String tipoPersonaJuridica = context.formParam("tipoPersonaJuridica");
        String rubro = context.formParam("rubro");
        String nombreUsuario = context.formParam("nombreUsuario");
        String password = context.formParam("password");

        Colaborador personaJuridica = new Colaborador();//razonSocial, tipoPersonaJuridica, rubro, nombreUsuario, password);
        personaRepositorio.agregar(personaJuridica);
        context.status(HttpStatus.CREATED).redirect("/login");
    }

    public void registrarHumana(Context context) {
        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String nombreUsuario = context.formParam("nombreUsuario");
        String password = context.formParam("password");

        Colaborador personaHumana = new Colaborador();//nombre, apellido, nombreUsuario, password);
        personaRepositorio.agregar(personaHumana);
        context.status(HttpStatus.CREATED).redirect("/login");
    }
}
