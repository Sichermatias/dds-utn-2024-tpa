package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Provincia;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.persona.*;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.dtos.georef.ProvinciaMunicipioGeorefDTO;
import ar.edu.utn.frba.dds.dtos.georef.PuntoGeorefDTO;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.LocalidadRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;
import ar.edu.utn.frba.dds.services.GeorefService;
import ar.edu.utn.frba.dds.services.LocalidadService;
import ar.edu.utn.frba.dds.services.ProvinciaService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RegistroUsuariosController implements WithSimplePersistenceUnit {
    private final ColaboradorRepositorio personaRepositorio;
    private final LocalidadRepositorio localidadRepositorio;

    public RegistroUsuariosController(ColaboradorRepositorio personaRepositorio, LocalidadRepositorio localidadRepositorio) {
        this.personaRepositorio = personaRepositorio;
        this.localidadRepositorio= localidadRepositorio;
    }

    public void elegirTipo(Context context) {
        context.render("registro/Registro.hbs");
    }

    public void formularioJuridica(Context context) {
        context.render("registro/Registro-Juridica.hbs");
    }

    public void formularioHumana(Context context) {
        context.render("registro/Registro-Humana.hbs");
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

        //Traer la localidad y la provincia con georef
        Localidad localidad = null;
        try {
            ProvinciaMunicipioGeorefDTO provinciaMunicipio = GeorefService.getProvinciaMunicipio(new PuntoGeorefDTO(latitud, longitud));

            LocalidadService localidadService = new LocalidadService();
            localidad = localidadService.getLocalidad(provinciaMunicipio);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Crear objeto de ubicación
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);
        ubicacion.setLocalidad(localidad);

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

        List<Rol> rol = personaRepositorio.buscarPorRol(Rol.class,"PersonaJuridica");
        usuario.setRol(rol.get(0));

        colaborador.setPuntaje(0.0);
        colaborador.setUsuario(usuario);

        personaRepositorio.persistir(colaborador);

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

        //Traer la localidad y la provincia con georef
        Localidad localidad = null;
        try {
            ProvinciaMunicipioGeorefDTO provinciaMunicipio = GeorefService.getProvinciaMunicipio(new PuntoGeorefDTO(latitud, longitud));

            LocalidadService localidadService = new LocalidadService();
            localidad = localidadService.getLocalidad(provinciaMunicipio);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Crear objeto de ubicación
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);
        ubicacion.setLocalidad(localidad);

        Tarjeta tarjeta=new Tarjeta();
        tarjeta.setActivo(true);
        tarjeta.setFechaHoraAlta(LocalDateTime.now());
        tarjeta.setCodigo(nroDocumento+fechaNacimiento);

        // Crear el objeto Colaborador
        Colaborador colaborador = new Colaborador();
        colaborador.setTipoPersona(TipoPersona.HUMANA);
        colaborador.setNombre(nombre);
        colaborador.setApellido(apellido);
        colaborador.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
        colaborador.setNroDocumento(nroDocumento);
        colaborador.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        colaborador.setUbicacion(ubicacion);
        colaborador.getTarjetas().add(tarjeta);

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

        List<Rol> rol = personaRepositorio.buscarPorRol(Rol.class,"PersonaHumana");
        usuario.setRol(rol.get(0));

        colaborador.setPuntaje(0.0);
        colaborador.setUsuario(usuario);

        personaRepositorio.persistir(colaborador);

        context.status(HttpStatus.CREATED).redirect("/login");
    }
    public void checkUsername(Context ctx) {
        UsuarioRepositorio usuarioRepositorio = UsuarioRepositorio.getInstancia();
        String username = ctx.queryParam("username");
        boolean isAvailable = usuarioRepositorio.buscarPorNombre(Usuario.class, username).isEmpty();
        ctx.json(isAvailable);
    }

}