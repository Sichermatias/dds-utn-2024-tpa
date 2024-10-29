package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.recomendador.RecomendadorPuntosDonacionReceiveDTO;
import ar.edu.utn.frba.dds.dtos.recomendador.RecomendadorPuntosDonacionSendDTO;
import ar.edu.utn.frba.dds.services.RecomendadorPuntosService;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class RecomendadorPuntosDonacionController {
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();

        context.render("recomendacionPuntosDonacion/recomendacion_puntos_donacion.hbs", model);
    }
    public void recomendarPuntos(Context context) {
        String latitud = context.formParam("latitud");
        String longitud = context.formParam("longitud");
        String radio = context.formParam("radio");
        String horario = context.formParam("horarioBuscado");
        String dias = context.formParam("diasBuscados");

        RecomendadorPuntosDonacionSendDTO puntosDonacionDTO = new RecomendadorPuntosDonacionSendDTO(latitud, longitud, radio, horario, dias);

        //Object puntosRecomendados = RecomendadorPuntosService::getPuntosRecomendadosDonacion(puntosDonacionDTO);

        Map<String, Object> model = new HashMap<>();
        model.put("latitud", latitud);
        model.put("longitud", longitud);
        model.put("radio", radio);
        model.put("horario", horario);
        model.put("dias", dias);

        //model.put("puntosRecomendados", puntosRecomendados);


        context.render("recomendacionPuntosDonacion/recomendacion_puntos_donacion.hbs", model);
    }

}

