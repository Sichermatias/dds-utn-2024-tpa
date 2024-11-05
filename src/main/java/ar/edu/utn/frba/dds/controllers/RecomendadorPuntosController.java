package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.calculadorDePuntos.ListadoDePuntosRecomendados;
import ar.edu.utn.frba.dds.dominio.services.recomendadorDePuntos.RecomendadorDePuntos;

import java.io.IOException;

public class RecomendadorPuntosController {
    public static ListadoDePuntosRecomendados obtenerPuntosRecomendados(Double longitud, Double latitud, Double radio) throws IOException {
        RecomendadorDePuntos recomendador = new RecomendadorDePuntos();

        return recomendador.listadoDePuntosRecomendados(longitud, latitud, radio);
    }
}
