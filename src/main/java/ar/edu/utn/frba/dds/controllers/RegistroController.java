package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroController implements WithSimplePersistenceUnit {
    private final ColaboradorRepositorio personaRepositorio;

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

        // Crear objeto de ubicación
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);

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


        // Persistir Colaborador

        colaborador.setUsuario(usuario);

        personaRepositorio.persistir(colaborador);

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

        // Crear objeto de ubicación
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);

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

        colaborador.setUsuario(usuario);
        // Persistir Colaborador
        personaRepositorio.persistir(colaborador);
        // Redireccionar a la página de login
        context.status(HttpStatus.CREATED).redirect("/login");
    }
    public void checkUsername(Context ctx) {
        UsuarioRepositorio usuarioRepositorio = UsuarioRepositorio.getInstancia();
        String username = ctx.queryParam("username");
        boolean isAvailable = usuarioRepositorio.buscarPorNombre(Usuario.class, username).isEmpty();
        ctx.json(isAvailable);
    }
    public void indexRegistroVulnerable(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        System.out.print(tipoRol);
        System.out.print(usuarioId);
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
        }
        context.render("Registro-Vulnerable.hbs", model);
    }
    public void registroVulnerable(Context context){
    }
}