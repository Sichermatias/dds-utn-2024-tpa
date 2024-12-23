package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.reportes.ViandasDonadasPorColaborador;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ViandasDonadasColaboradorRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViandasDonadasColaboradorController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    public ViandasDonadasColaboradorController(ViandasDonadasColaboradorRepositorio viandasDonadasColaborador) {
    }

    @Override
    public void create(Context context) {

    }
    private Colaborador obtenerColaboradorDeSesion(Context context) {
        ColaboradorRepositorio colaboradorRepositorio= ColaboradorRepositorio.getInstancia();
        Long usuarioId = context.sessionAttribute("usuario_id");
        return colaboradorRepositorio.buscarPorIdUsuario(usuarioId);
    }

    public void index(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        String tipoRol = context.sessionAttribute("tipo_rol");
        if (usuarioId == null) {
            context.redirect("/login");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("usuario_id", usuarioId);
        model.put("tipo_rol", tipoRol);

        ViandasDonadasColaboradorRepositorio repositorio = ViandasDonadasColaboradorRepositorio.getInstancia();
        List<ViandasDonadasPorColaborador> viandasDonadasPorColaboradorList = repositorio.buscarUltimos7Dias();

        model.put("donadasPorColaborador", viandasDonadasPorColaboradorList.isEmpty() ? null : viandasDonadasPorColaboradorList);

        context.render("viandasDonadasColaborador/donadas_colaborador.hbs", model);
    }

    @Override
    public void show(Context context){

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
