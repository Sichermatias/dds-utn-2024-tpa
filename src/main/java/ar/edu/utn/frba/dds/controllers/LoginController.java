package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginController implements ICrudViewsHandler{

     private UsuarioRepositorio usuarioRepositorio;

    public LoginController(UsuarioRepositorio usuarioRepositorio) {
           this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        if(context.sessionAttribute("usuario_id")!= null){
            model.put("logeado",true);
           //model.put("administrador",esAdmin(context));
            context.render("Landing-Page.hbs", model);
        }else{
            model.put("logeado",false);

            context.render("Login.hbs", model);
        }

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {
        List<Usuario> usuarios = usuarioRepositorio.buscarPorNombre(Usuario.class, context.formParam("username"));
        if (!usuarios.isEmpty()) {
            Usuario usuario = usuarios.get(0);
            if (usuario.getContrasenia().equals(context.formParam("password"))) {
                // Almacenar el ID del usuario y el rol en la sesion
                context.sessionAttribute("usuario_id", usuario.getId());
                context.sessionAttribute("tipo_rol", usuario.getRol().getTipo().name());
                context.redirect("/");
            } else {
                // Manejar el caso donde la contraseña es incorrecta
                context.redirect("/login?error=contraseña_incorrecta");
            }
        } else {
            // Manejar el caso donde no se encuentra el usuario
            context.redirect("/login?error=usuario_no_encontrado");
        }
    }


    @Override
    public void delete(Context context) {

    }

    private void asignarParametros(Usuario usuario, Context context) {
        if (!Objects.equals(context.formParam("usuario"), "")) {
            usuario.setNombreUsuario(context.formParam("usuario"));

        }
        if (!Objects.equals(context.formParam("contrasena"), "")) {
            usuario.setContrasenia(context.formParam("contrasena"));
        }

    }
}
