package ar.edu.utn.frba.dds.controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.Map;

public class LandingPageController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        //model.put("fallo", false);
        context.consumeSessionAttribute("logeado");
        context.consumeSessionAttribute("administrador");

        model.put("logeado",false);
        context.render("landingPage.hbs", model); //TODO:NOMBRE CORRECTO DEL ARCHIVO HBS
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
