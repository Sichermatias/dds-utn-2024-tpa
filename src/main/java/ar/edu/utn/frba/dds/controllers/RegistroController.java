package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import ar.edu.utn.frba.dds.dominio.persona.*;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.TipoRol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.modelmapper.internal.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RegistroController implements WithSimplePersistenceUnit {
    private ColaboradorRepositorio personaRepositorio;

    public RegistroController(ColaboradorRepositorio personaRepositorio) {
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

        // Imprimir los datos recibidos
        System.out.println("Datos recibidos para el registro de la persona jurídica:");
        System.out.println("Razón Social: " + context.formParam("razonSocial"));
        System.out.println("Tipo de Persona Jurídica: " + context.formParam("tipoPersonaJuridica"));
        System.out.println("Rubro: " + context.formParam("rubro"));
        System.out.println("Dirección: " + context.formParam("direccion"));
        System.out.println("Nombre de Contacto: " + context.formParam("nombreContacto"));
        System.out.println("Dato de Contacto: " + context.formParam("contacto"));
        System.out.println("Nombre de Usuario: " + context.formParam("nombreUsuario"));


        Colaborador colaborador = new Colaborador();

        colaborador.setTipoPersona(TipoPersona.JURIDICA);

        colaborador.setRazonSocial(context.formParam("razonSocial"));

        String rubro = context.formParam("rubro");
        RubroPersonaJuridica rubroPersonaJuridica= new RubroPersonaJuridica();
        rubroPersonaJuridica.setNombre(rubro);
        colaborador.setRubroPersonaJuridica(rubroPersonaJuridica);

        String tipo= context.formParam("tipoPersonaJuridica");
        TipoPersonaJuridica tipoPersonaJuridica = new TipoPersonaJuridica();
        tipoPersonaJuridica.setNombre(tipo);
        colaborador.setTipoPersonaJuridica(tipoPersonaJuridica);

        NombreDeMedioDeContacto nombreDeMedioDeContacto= new NombreDeMedioDeContacto(context.formParam("nombreContacto"));

        MedioDeContacto medioDeContacto = new MedioDeContacto(nombreDeMedioDeContacto,context.formParam("contacto"));

        colaborador.agregarMedioDeContacto(medioDeContacto);

        colaborador.setFechaHoraAlta(LocalDateTime.now());
        personaRepositorio.persistir(colaborador);

        String nombreUsuario = context.formParam("nombreUsuario");
        String password = context.formParam("password");

        Usuario usuario= new Usuario();
        usuario.setContrasenia(password);
        usuario.setNombreUsuario(nombreUsuario);
        Rol rol = new Rol();
        rol.setTipo(TipoRol.COLABORADOR_JURIDICO);
        rol.setNombreRol("Usuario");
        usuario.setRol(rol);
        UsuarioRepositorio.getInstancia().persistir(usuario);

        context.status(HttpStatus.CREATED).redirect("/login");
    }

    public void registrarHumana(Context context) {
        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String nombreUsuario = context.formParam("nombreUsuario");
        String password = context.formParam("password");

        Colaborador personaHumana = new Colaborador();//nombre, apellido, nombreUsuario, password);
        personaRepositorio.persistir(personaHumana);
        context.status(HttpStatus.CREATED).redirect("/login");
    }
}
