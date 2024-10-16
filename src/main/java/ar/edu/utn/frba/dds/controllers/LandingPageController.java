package ar.edu.utn.frba.dds.controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.Map;

public class LandingPageController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        String usuarioId= context.sessionAttribute("usuario_id");
        System.out.print(tipoRol);
        System.out.print(usuarioId);
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
        }
        context.render("Landing-Page.hbs", model);
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
