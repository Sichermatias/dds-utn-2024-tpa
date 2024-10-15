package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Provincia;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        String razonSocial = context.formParam("razonSocial");
        String tipoPersonaJuridica = context.formParam("tipoPersonaJuridica");
        String rubro = context.formParam("rubro");
        String direccion = context.formParam("direccion");
        String latitud = context.formParam("lat");
        String longitud = context.formParam("lng");

        List<String> nombresContacto = context.formParams("nombreContacto[]");
        List<String> datosContacto = context.formParams("contacto[]");

        System.out.println("Dirección: " + direccion);
        // Extraer la ciudad y localidad desde la dirección
        String[] completa= direccion.split(",");
        System.out.println("Ciudad: " + completa[3]);
        String ciudad = completa[2];
        System.out.println("Localidad: " + completa[5]);
        String localidad = completa[5];

        // Crear objeto de ubicación
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);

        // Asignar ciudad y localidad
        Localidad loc = new Localidad();
        loc.setNombreLocalidad(localidad);

        Provincia provincia = new Provincia();
        provincia.setNombreProvincia(ciudad);

        loc.setProvincia(provincia);
        ubicacion.setLocalidad(loc);

        // Crear el objeto Colaborador
        Colaborador colaborador = new Colaborador();
        colaborador.setTipoPersona(TipoPersona.JURIDICA);
        colaborador.setRazonSocial(razonSocial);
        colaborador.setUbicacion(ubicacion);

        // Asignar rubro
        RubroPersonaJuridica rubroPersonaJuridica = new RubroPersonaJuridica();
        rubroPersonaJuridica.setNombre(rubro);
        colaborador.setRubroPersonaJuridica(rubroPersonaJuridica);

        // Asignar tipo de persona jurídica
        TipoPersonaJuridica tipoPersona = new TipoPersonaJuridica();
        tipoPersona.setNombre(tipoPersonaJuridica);
        colaborador.setTipoPersonaJuridica(tipoPersona);

        // Agregar medios de contacto
        for (int i = 0; i < nombresContacto.size(); i++) {
            NombreDeMedioDeContacto nombreMedio = new NombreDeMedioDeContacto(nombresContacto.get(i));
            MedioDeContacto medioDeContacto = new MedioDeContacto(nombreMedio, datosContacto.get(i));
            colaborador.agregarMedioDeContacto(medioDeContacto);
        }

        // Fecha de alta
        colaborador.setFechaHoraAlta(LocalDateTime.now());

        // Persistir Colaborador
        personaRepositorio.persistir(colaborador);

        // Crear y persistir Usuario
        String nombreUsuario = context.formParam("nombreUsuario");
        String password = context.formParam("password");

        Usuario usuario = new Usuario();
        usuario.setContrasenia(password);
        usuario.setNombreUsuario(nombreUsuario);

        Rol rol = new Rol();
        rol.setTipo(TipoRol.COLABORADOR_JURIDICO);
        rol.setNombreRol("Usuario");
        usuario.setRol(rol);

        UsuarioRepositorio.getInstancia().persistir(usuario);

        // Redireccionar a la página de login
        context.status(HttpStatus.CREATED).redirect("/login");
    }



    public void registrarHumana(Context context) {

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String tipoDocumento = context.formParam("tipoDocumento");
        String nroDocumento = context.formParam("nroDocumento");
        String fechaNacimiento = context.formParam("fechaNacimiento");
        String direccion = context.formParam("direccion");
        String latitud = context.formParam("lat");
        String longitud = context.formParam("lng");

        List<String> nombresContacto = context.formParams("nombreContacto[]");
        List<String> datosContacto = context.formParams("contacto[]");

        System.out.println("Dirección: " + direccion);
        // Extraer la ciudad y localidad desde la dirección
        String[] completa= direccion.split(",");
        System.out.println("Ciudad: " + completa[3]);
        String ciudad = completa[2];
        System.out.println("Localidad: " + completa[5]);
        String localidad = completa[5];

        // Crear objeto de ubicación
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);

        // Asignar ciudad y localidad
        Localidad loc = new Localidad();
        loc.setNombreLocalidad(localidad);

        Provincia provincia = new Provincia();
        provincia.setNombreProvincia(ciudad);

        loc.setProvincia(provincia);
        ubicacion.setLocalidad(loc);

        // Crear el objeto Colaborador
        Colaborador colaborador = new Colaborador();
        colaborador.setTipoPersona(TipoPersona.HUMANA);
        colaborador.setNombre(nombre);
        colaborador.setApellido(apellido);
        colaborador.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
        colaborador.setNroDocumento(nroDocumento);
        colaborador.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        colaborador.setUbicacion(ubicacion);

        // Agregar medios de contacto
        for (int i = 0; i < nombresContacto.size(); i++) {
            NombreDeMedioDeContacto nombreMedio = new NombreDeMedioDeContacto(nombresContacto.get(i));
            MedioDeContacto medioDeContacto = new MedioDeContacto(nombreMedio, datosContacto.get(i));
            colaborador.agregarMedioDeContacto(medioDeContacto);
        }

        // Fecha de alta
        colaborador.setFechaHoraAlta(LocalDateTime.now());

        // Persistir Colaborador
        personaRepositorio.persistir(colaborador);

        // Crear y persistir Usuario
        String nombreUsuario = context.formParam("nombreUsuario");
        String password = context.formParam("password");

        Usuario usuario = new Usuario();
        usuario.setContrasenia(password);
        usuario.setNombreUsuario(nombreUsuario);

        Rol rol = new Rol();
        rol.setTipo(TipoRol.COLABORADOR_HUMANO);
        rol.setNombreRol("Usuario");
        usuario.setRol(rol);

        UsuarioRepositorio.getInstancia().persistir(usuario);

        // Redireccionar a la página de login
        context.status(HttpStatus.CREATED).redirect("/login");
    }
}
