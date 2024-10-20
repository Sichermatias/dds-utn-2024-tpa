package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ColaboracionController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        /*String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        String path= context.path();
        System.out.print(tipoRol);
        System.out.print(usuarioId);
        if (tipoRol != null) {
            model.put("path", path);
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
        }*/

        String tipoRol = "COLABORADOR_HUMANO";

        switch (tipoRol) {
            case "COLABORADOR_JURIDICO":
                context.render("colaboraciones/colaboraciones_persona_juridica.hbs", model);
                break;
            case "COLABORADOR_HUMANO":
                context.render("colaboraciones/colaboraciones_persona_humana.hbs", model);
                break;
            default:
                context.render("Landing-Page.hbs", model);
                break;
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

    }

    @Override
    public void delete(Context context) {

    }
}
