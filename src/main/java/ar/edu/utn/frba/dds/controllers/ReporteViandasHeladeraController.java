package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.reportes.ReporteViandasHeladera;
import ar.edu.utn.frba.dds.models.repositories.imp.ViandasHeladeraReporteRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteViandasHeladeraController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private final ViandasHeladeraReporteRepositorio viandasHeladeraReporteRepositorio = new ViandasHeladeraReporteRepositorio();

    public ReporteViandasHeladeraController() {
    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void index(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        if (usuarioId == null) {
            context.redirect("/login");
            return;
        }

        String tipoRol = context.sessionAttribute("tipo_rol");
        Map<String, Object> model = new HashMap<>();
        model.put("usuario_id", usuarioId);
        model.put("tipo_rol", tipoRol);

        List<ReporteViandasHeladera> viandasPorHeladera = this.viandasHeladeraReporteRepositorio.buscarUltimos7Dias();

        model.put("viandasPorHeladera", viandasPorHeladera);
        context.render("viandasHeladera/viandas_heladera.hbs", model);
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
