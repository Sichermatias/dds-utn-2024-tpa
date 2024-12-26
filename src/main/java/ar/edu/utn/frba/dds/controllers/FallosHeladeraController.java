package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.reportes.FallosPorHeladera;
import ar.edu.utn.frba.dds.models.repositories.imp.FallosHeladeraRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FallosHeladeraController implements ICrudViewsHandler, WithSimplePersistenceUnit{
    private final FallosHeladeraRepositorio fallosHeladeraRepositorio;

    public FallosHeladeraController(FallosHeladeraRepositorio fallosHeladeraRepositorio) {
        this.fallosHeladeraRepositorio = fallosHeladeraRepositorio;
    }
    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);
        List<FallosPorHeladera> fallosPorHeladeras= this.fallosHeladeraRepositorio.buscarUltimos7Dias();
        if (usuarioId != null) {
            model.put("fallosPorHeladeras", fallosPorHeladeras);
            context.render("fallosPorHeladera/reportes/fallos_heladera.hbs", model);
        }
        else context.redirect("/login");
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
