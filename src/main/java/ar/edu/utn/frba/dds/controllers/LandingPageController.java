package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.server.DDMetricsUtils;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.micrometer.core.instrument.step.StepMeterRegistry;

import java.util.HashMap;
import java.util.Map;

public class LandingPageController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    final DDMetricsUtils metricsUtils = new DDMetricsUtils("inicio");
    final StepMeterRegistry registry = metricsUtils.getRegistry();

    @Override
    public void index(Context context) {
        registry.counter("inicio","status","ok").increment();

        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);
        context.render("Landing-Page.hbs", model);
    }

    public void indexNosotros(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);
        context.render("Nosotros.hbs", model);
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
