package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;

import java.util.*;

public class LoginController implements ICrudViewsHandler{

    private final UsuarioRepositorio usuarioRepositorio;

    public LoginController(UsuarioRepositorio usuarioRepositorio) {
           this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();

        String error = context.sessionAttribute("login_error");
        context.sessionAttribute("login_error", null);
        model.put("error", error);

        String returnPath = context.queryParam("return");

        if (returnPath != null)
            model.put("return", "?return=" + returnPath);
        else
            model.put("return", "");

        context.render("/login/Login.hbs", model);
    }
    public void logout(Context context){
        context.sessionAttribute("usuario_id", null);
        context.sessionAttribute("tipo_rol", null);
        context.redirect("/");
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
                String returnPath = context.queryParam("return");
                System.out.println(returnPath);
                context.sessionAttribute("usuario_id", usuario.getId());
                context.sessionAttribute("tipo_rol", usuario.getRol().getTipo().name());
                if(returnPath == null)
                    context.redirect("/");
                else
                    context.redirect("/" + returnPath);
            } else {
                // Manejar el caso donde la contraseña es incorrecta
                context.sessionAttribute("login_error", "Credenciales incorrectas");
                context.redirect("/login");
            }
        } else {
            // Manejar el caso donde no se encuentra el usuario
            context.sessionAttribute("login_error", "Credenciales incorrectas");
            context.redirect("/login");
        }
    }
    public void error(Context context) {

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
