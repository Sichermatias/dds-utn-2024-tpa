package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.calculadorDePuntos.ListadoDePuntosRecomendados;
import ar.edu.utn.frba.dds.dominio.calculadorDePuntos.PuntoRecomendado;
import ar.edu.utn.frba.dds.dominio.services.recomendadorDePuntos.RecomendadorDePuntos;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecomendadorPuntosController {

    public ListadoDePuntosRecomendados obtenerPuntosRecomendados(Context context) throws IOException {
        RecomendadorDePuntos recomendador = new RecomendadorDePuntos();
        Double latitud = context.bodyAsClass(RequestParams.class).latitud;
        Double longitud = context.bodyAsClass(RequestParams.class).longitud;
        Double radio = context.bodyAsClass(RequestParams.class).radio;

        ListadoDePuntosRecomendados puntos = new ListadoDePuntosRecomendados();

        List<PuntoRecomendado> lista = new ArrayList<>();

        PuntoRecomendado punto1 = new PuntoRecomendado();
        punto1.setLatitud(-34.59695673218942);
        punto1.setLongitud(-58.422046423547734);
        lista.add(punto1);

        PuntoRecomendado punto2 = new PuntoRecomendado();
        punto2.setLatitud(-34.60309624793624);
        punto2.setLongitud(-58.42109584860738);
        lista.add(punto2);

        puntos.setPuntosRecomendados(lista);

        context.json(puntos);

        return null;

        // return recomendador.listadoDePuntosRecomendados(longitud, latitud, radio);
    }

    public static class RequestParams {
        public Double latitud;
        public Double longitud;
        public Double radio;
    }
}