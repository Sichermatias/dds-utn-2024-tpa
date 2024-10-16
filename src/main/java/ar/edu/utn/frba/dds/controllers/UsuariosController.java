package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;

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

    public void nosotros(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        System.out.print(tipoRol);
        System.out.print(usuarioId);
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
        }
        context.render("Nosotros.hbs", model);
    }
    @Override
    public void index(Context context) {
        System.out.println((Long)context.sessionAttribute("usuario_id"));
        Map<String, Object> model = new HashMap<>();

        model.put("logeado",true);
    //    model.put("administrador",esAdmin(context));
        context.render("administracionUsuarios/administracionUsuarios.liquid", model);
    }

    @Override
    public void show(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = usuarioRepositorio.buscarPorId(Usuario.class,Long.parseLong(context.pathParam("id")));
        model.put("usuarios", usuario);
        model.put("logeado",true);
    //    model.put("administrador",esAdmin(context));
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
/*            if(usuarioNuevo.getPersona()!=null && usuarioNuevo.getPersona().getMail()!=null){
                if(usuarioActual.getPersona()==null){
                    usuarioActual.setPersona(new Persona());
                }
                usuarioActual.getPersona().setMail(usuarioNuevo.getPersona().getMail());
            }
*/
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
//        model.put("administrador",esAdmin(context));
        context.render("administracionUsuarios/listarUsuarios.hbs",model);
    }
    private void asignarParametros(Usuario usuario, Context context) {
        if (!Objects.equals(context.formParam("nombre"), "")) {
            usuario.setNombreUsuario(context.formParam("nombre"));
        }
        if (!Objects.equals(context.formParam("contrasena"), "")) {
            usuario.setContrasenia(context.formParam("contrasena"));
        }
        /*
        Persona persona = new Persona();
        usuario.setPersona(persona);
        if (!Objects.equals(context.formParam("correo"), "")) {
            persona.setMail(context.formParam("correo"));
            usuario.setPersona(persona);
        }
        if (!Objects.equals(context.formParam("rol"), "")) {
            if(Objects.equals(context.formParam("rol"), "admin")) {
                usuario.setRol(Rol.ADMIN);
            }
            if(Objects.equals(context.formParam("rol"), "miembro")) {
                usuario.setRol(Rol.MIEMBRO);
            }
        }
    */
    }
}
