package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class FallasTecnicasController implements WithSimplePersistenceUnit {

    public void indexFormularioFallaTecnica(Context context) {
        Map<String, Object> model = new HashMap<>();
        context.render("/reporte/falla_tecnicas.hbs", model);
    }

    public void formularioFallaTecnica(Context context){

    }


}
