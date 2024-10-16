package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class PremiosController extends Controller implements ICrudViewsHandler {
    private final ColaboradorRepositorio colaboradorRepositorio;

    public PremiosController(ColaboradorRepositorio colaboradorRepositorio) {
        this.colaboradorRepositorio = colaboradorRepositorio;
    }

    @Override
    public void index(Context context) {
        Usuario usuario = this.usuarioLogueado(context);
        Colaborador colaborador = this.colaboradorRepositorio.buscarPorIdUsuario(usuario.getId());
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        System.out.print(tipoRol);
        System.out.print(usuarioId);
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
        }
        model.put("puntaje", colaborador.getPuntaje());
        context.render("Puntos-Humano.hbs", model);
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

    }

    @Override
    public void delete(Context context) {

    }
}
