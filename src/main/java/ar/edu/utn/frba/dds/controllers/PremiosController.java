package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.Premio;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.PremioRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PremiosController extends Controller implements ICrudViewsHandler {
    private final ColaboradorRepositorio colaboradorRepositorio;

    private final PremioRepositorio premioRepositorio;

    public PremiosController(ColaboradorRepositorio colaboradorRepositorio, PremioRepositorio premioRepositorio) {
        this.colaboradorRepositorio = colaboradorRepositorio;
        this.premioRepositorio = premioRepositorio;
    }

    @Override
    public void index(Context context) {
        Usuario usuario = this.usuarioLogueado(context);
        Colaborador colaborador = this.colaboradorRepositorio.buscarPorIdUsuario(usuario.getId());
        Map<String, Object> model = new HashMap<>();
        Long usuarioId= context.sessionAttribute("usuario_id");
        String tipoRol = usuario.nombreTipoRol();
        if (tipoRol != null) {
            model.put("usuario_id", usuarioId);
            model.put("tipo_rol", tipoRol);
            model.put("puntaje", colaborador.getPuntaje());
            List<Premio> premios = this.premioRepositorio.buscarTodos(Premio.class);
            model.put("premios", premios);
            List<String> rubros = this.premioRepositorio.buscarTodosLosRubros();
            model.put("rubros", rubros);
            context.render("Puntos-Humano.hbs", model);
        }else context.redirect("/login");
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
