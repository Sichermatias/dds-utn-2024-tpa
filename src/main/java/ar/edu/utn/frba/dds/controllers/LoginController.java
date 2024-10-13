package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//TODO: REPO USUARIOS Y USUARIOS EN GENERAL.
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
            context.render("/landingPage.hbs", model);
        }else{
            model.put("logeado",false);

            context.render("login.hbs", model);
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
        Map<String, Object> model = new HashMap<>();

        Usuario usuario = new Usuario();
        this.asignarParametros(usuario, context);
            List<Usuario> Todosusuarios = usuarioRepositorio.buscarTodos(Usuario.class);
            Usuario usuarioRegistrado = usuarioRepositorio.usuarioRegistrado(usuario, Todosusuarios);
        if(usuarioRegistrado!=null ){
            context.sessionAttribute("usuario_id", usuarioRegistrado.getId());
          model.put("logeado",true);
        //model.put("administrador",esAdmin(context));
        context.render("/landingPage.hbs", model);
        }
        else{
            context.redirect("/");
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
