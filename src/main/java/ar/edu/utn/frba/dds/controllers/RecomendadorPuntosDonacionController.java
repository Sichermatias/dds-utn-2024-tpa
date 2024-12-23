package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.recomendador.RecomendadorPuntosDonacionReceiveDTO;
import ar.edu.utn.frba.dds.dtos.recomendador.RecomendadorPuntosDonacionSendDTO;
import ar.edu.utn.frba.dds.services.RecomendadorPuntosDonacionService;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecomendadorPuntosDonacionController {
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();

        context.render("recomendacionPuntosDonacion/recomendacion_puntos_donacion.hbs", model);
    }
    public void recomendarPuntos(Context context) {
        String latitud = context.formParam("latitud");
        String longitud = context.formParam("longitud");
        String radio = context.formParam("radio");
        String horario = Objects.requireNonNull(context.formParam("horarioBuscado")).replace(':', '.');
        String dias = context.formParam("diasBuscados");

        RecomendadorPuntosDonacionSendDTO puntosDonacionDTO = new RecomendadorPuntosDonacionSendDTO(latitud, longitud, radio, horario, dias);

        List<RecomendadorPuntosDonacionReceiveDTO> puntosRecomendados = RecomendadorPuntosDonacionService.getPuntosRecomendadosDonacion(puntosDonacionDTO);

        Map<String, Object> model = new HashMap<>();
        model.put("latitudForm", latitud);
        model.put("longitudForm", longitud);
        model.put("radioForm", radio);
        model.put("horarioForm", horario);
        model.put("diasForm", dias);

        model.put("puntosRecomendados", puntosRecomendados);

        context.render("recomendacionPuntosDonacion/recomendacion_puntos_donacion.hbs", model);
    }

}

