package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.RubroPersonaJuridica;
import ar.edu.utn.frba.dds.dominio.persona.TipoDocumento;
import ar.edu.utn.frba.dds.dominio.persona.TipoPersonaJuridica;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.TipoRol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//TODO: REPO USUARIOS Y USUARIOS EN GENERAL.


public class UsuariosController implements ICrudViewsHandler, WithSimplePersistenceUnit {
     private UsuarioRepositorio usuarioRepositorio;

    public UsuariosController(UsuarioRepositorio usuarioRepositorio) {
          this.usuarioRepositorio = usuarioRepositorio;
    }

    public void indexPerfil(Context context){
        ColaboradorRepositorio repositorioColaborador = ColaboradorRepositorio.getInstancia();

        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");

        Colaborador colaborador = repositorioColaborador.obtenerColaboradorPorUsuarioId(usuarioId);

        if (colaborador != null) {
            model.put("colaborador", colaborador);
            model.put("tipo_rol", tipoRol);
            switch (tipoRol) {
                case "COLABORADOR_HUMANO":
                    context.render("/perfil/Perfil_Humano.hbs", model);
                    break;
                case "COLABORADOR_JURIDICO":
                    context.render("/perfil/Perfil_Juridico.hbs", model);
                    break;
                case default:
                    context.render("LandingPage.hbs",model);
                    break;
            }
        } else {
            context.redirect("/login");
        }
    }
    public void indexEditJuridica(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        ColaboradorRepositorio repositorioColaborador = ColaboradorRepositorio.getInstancia();
        if (usuarioId != null) {
            Colaborador colaborador = repositorioColaborador.obtenerColaboradorPorUsuarioId(usuarioId);
            if (colaborador != null) {
                context.attribute("colaborador", colaborador);
                context.attribute("direccion", colaborador.getUbicacion().getDireccion());
                context.render("/perfil/editar_perfil_juridica.hbs");
            } else {
                context.redirect("/login");
            }
        } else {
            context.redirect("/login");
        }
    }

    public void editJuridica(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        ColaboradorRepositorio repositorioColaborador = ColaboradorRepositorio.getInstancia();
        UsuarioRepositorio repositorioUsuario = UsuarioRepositorio.getInstancia();
        if (usuarioId != null) {
            Colaborador colaborador = repositorioColaborador.obtenerColaboradorPorUsuarioId(usuarioId);
            if (colaborador != null) {
                colaborador.setRazonSocial(context.formParam("razonSocial"));
                TipoPersonaJuridica tipoPersonaJuridica = new TipoPersonaJuridica();
                tipoPersonaJuridica.setNombre(context.formParam("tipoPersonaJuridica"));
                colaborador.setTipoPersonaJuridica(tipoPersonaJuridica);
                RubroPersonaJuridica rubroPersonaJuridica=new RubroPersonaJuridica();
                rubroPersonaJuridica.setNombre(context.formParam("rubro"));
                colaborador.setRubroPersonaJuridica(rubroPersonaJuridica);

                List<String> nombresContacto = context.formParams("nombreContacto[]");
                List<String> datosContacto = context.formParams("contacto[]");

                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setDireccion(context.formParam("direccion"));
                ubicacion.setLatitud(context.formParam("lat"));
                ubicacion.setLongitud(context.formParam("lng"));
                colaborador.setUbicacion(ubicacion);

                colaborador.getMediosDeContacto().clear(); // Limpiar los medios de contacto existentes
                for (int i = 0; i < nombresContacto.size(); i++) {
                    NombreDeMedioDeContacto nombreMedio = new NombreDeMedioDeContacto(nombresContacto.get(i));
                    MedioDeContacto medioDeContacto = new MedioDeContacto(nombreMedio, datosContacto.get(i));
                    colaborador.agregarMedioDeContacto(medioDeContacto);
                }

                String nombreUsuario = context.formParam("nombreUsuario");
                String password = context.formParam("password");

                Usuario usuario = repositorioUsuario.buscarPorId(Usuario.class, usuarioId);
                if (!password.isEmpty()) {
                    usuario.setContrasenia(password); // Solo actualiza si se proporciona una nueva contraseña
                }
                usuario.setNombreUsuario(nombreUsuario);

                colaborador.setUsuario(usuario);

                repositorioUsuario.persistir(usuario);
                repositorioColaborador.persistir(colaborador);

                context.redirect("/perfil");
            }
        } else {
            context.redirect("/login");
        }
    }

    public void indexEditHumana(Context context){
        Long usuarioId = context.sessionAttribute("usuario_id");
        ColaboradorRepositorio repositorioColaborador = ColaboradorRepositorio.getInstancia();
        if (usuarioId != null) {
            Colaborador colaborador = repositorioColaborador.obtenerColaboradorPorUsuarioId(usuarioId);
            if (colaborador != null) {
                context.attribute("colaborador", colaborador);
                context.attribute("usuario", colaborador.getUsuario());
                context.attribute("direccion", colaborador.getUbicacion().getDireccion());
                context.attribute("mediosContacto", colaborador.getMediosDeContacto());
                context.render("/perfil/editar_perfil_humana.hbs");
            } else {
                context.redirect("/login");
            }
        } else {
            context.redirect("/login");
        }
    }

    public void editHumana(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        ColaboradorRepositorio repositorioColaborador= ColaboradorRepositorio.getInstancia();
        UsuarioRepositorio repositorioUsuario=UsuarioRepositorio.getInstancia();
        if (usuarioId != null) {
            Colaborador colaborador = repositorioColaborador.obtenerColaboradorPorUsuarioId(usuarioId);
            if (colaborador != null) {
                colaborador.setNombre(context.formParam("nombre"));
                colaborador.setApellido(context.formParam("apellido"));
                colaborador.setTipoDocumento(TipoDocumento.valueOf(context.formParam("tipoDocumento")));
                colaborador.setNroDocumento(context.formParam("nroDocumento"));
                colaborador.setFechaNacimiento(LocalDate.parse(context.formParam("fechaNacimiento")));

                List<String> nombresContacto = context.formParams("nombreContacto[]");
                List<String> datosContacto = context.formParams("contacto[]");

                Ubicacion ubicacion=new Ubicacion();
                ubicacion.setDireccion(context.formParam("direccion"));
                ubicacion.setLatitud(context.formParam("lat"));
                ubicacion.setLongitud(context.formParam("lng"));
                colaborador.setUbicacion(ubicacion);


                for (int i = 0; i < nombresContacto.size(); i++) {
                    NombreDeMedioDeContacto nombreMedio = new NombreDeMedioDeContacto(nombresContacto.get(i));
                    MedioDeContacto medioDeContacto = new MedioDeContacto(nombreMedio, datosContacto.get(i));
                    colaborador.agregarMedioDeContacto(medioDeContacto);
                }

                colaborador.setFechaHoraAlta(LocalDateTime.now());

                String nombreUsuario = context.formParam("nombreUsuario");
                String password = context.formParam("password");

                Usuario usuario=repositorioUsuario.buscarPorId(Usuario.class, usuarioId);
                usuario.setContrasenia(password);
                usuario.setNombreUsuario(nombreUsuario);

                colaborador.setPuntaje(0);
                colaborador.setUsuario(usuario);

                repositorioUsuario.persistir(usuario);
                repositorioColaborador.persistir(colaborador);

                context.status(HttpStatus.CREATED).redirect("/login");

                repositorioColaborador.persistir(colaborador);
                context.redirect("/perfil");
            }
            } else {
            context.redirect("/login");
        }
    }
    @Override
    public void index(Context context) {
        System.out.println((Long)context.sessionAttribute("usuario_id"));
        Map<String, Object> model = new HashMap<>();

        model.put("logeado",true);
        context.render("administracionUsuarios/administracionUsuarios.liquid", model);
    }

    @Override
    public void show(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = usuarioRepositorio.buscarPorId(Usuario.class,Long.parseLong(context.pathParam("id")));
        model.put("usuarios", usuario);
        model.put("logeado",true);
        context.render("administracionUsuarios/mostrarUsuario.hbs", model);
    }

    @Override
    public void create(Context context) {
    }

    @Override
    public void save(Context context) {
        withTransaction(()-> {
            Usuario usuario = new Usuario();
            this.asignarParametros(usuario, context);
           this.usuarioRepositorio.persistir(usuario);
        });
        context.redirect("/usuarios");
    }

    @Override
    public void edit(Context context) {
        withTransaction(()->{
            Usuario usuarioActual = (Usuario) this.usuarioRepositorio.buscarPorId(Usuario.class,Long.parseLong(context.pathParam("id")));
            Usuario usuarioNuevo = new Usuario();
            this.asignarParametros(usuarioNuevo,context);
            if(usuarioNuevo.getNombreUsuario()!= null){
                usuarioActual.setNombreUsuario(usuarioNuevo.getNombreUsuario());
            }
            if(usuarioNuevo.getNombreUsuario()!= null){
                usuarioActual.setContrasenia(usuarioNuevo.getContrasenia());
            }
            if(usuarioNuevo.getRol()!=null){
                usuarioActual.setRol(usuarioNuevo.getRol());

            }
        });
        context.redirect("/usuarios");
    }
    @Override
    public void delete(Context context) {
        withTransaction(()-> {
                    Usuario usuario = this.usuarioRepositorio.buscarPorId(Usuario.class, Long.parseLong(context.pathParam("id")));
                    System.out.println(context.body());
                    System.out.println(usuario);this.usuarioRepositorio.borrar(usuario);
                });
        context.redirect("/usuarios");
    }
    @Override
    public void update(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Usuario> Todosusuarios = usuarioRepositorio.buscarTodos(Usuario.class);
        model.put("usuarios", Todosusuarios);
        model.put("logeado",true);
        context.render("administracionUsuarios/listarUsuarios.hbs",model);
    }
    private void asignarParametros(Usuario usuario, Context context) {
        if (!Objects.equals(context.formParam("nombre"), "")) {
            usuario.setNombreUsuario(context.formParam("nombre"));
        }
        if (!Objects.equals(context.formParam("contrasena"), "")) {
            usuario.setContrasenia(context.formParam("contrasena"));
        }
    }
}
