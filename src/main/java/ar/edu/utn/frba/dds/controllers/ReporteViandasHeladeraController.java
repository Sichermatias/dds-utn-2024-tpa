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
    public ReporteViandasHeladeraController() {
    }

    @Override
    public void create(Context context) {

    }
    @Override
    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        ViandasHeladeraReporteRepositorio repositorio = ViandasHeladeraReporteRepositorio.getInstancia();
        System.out.println("test Test Test");
        List<ReporteViandasHeladera> viandasPorHeladera = repositorio.buscarTodas();

        if (tipoRol != null) {
            model.put("viandasPorHeladera", viandasPorHeladera);
            context.render("viandasHeladera/viandas_heladera.hbs", model);
        }
        else context.redirect("/login");
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
