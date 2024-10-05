package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.services.recomendadorDePuntos.RecomendadorDePuntos;
import ar.edu.utn.frba.dds.dominio.calculadorDePuntos.PuntoRecomendado;
import ar.edu.utn.frba.dds.dominio.calculadorDePuntos.ListadoDePuntosRecomendados;

import java.io.IOException;

public class EjemploDeUsoApiCalculadorPuntos{

    private static final double longitud = 10.23;
    private static final double latitud = 22.10;
    private static final double radio = 100;
    public static void main(String[] args) throws IOException {
        RecomendadorDePuntos recomendadorDePuntos = RecomendadorDePuntos.recomendadorDePuntos();

        ListadoDePuntosRecomendados listadoDePuntosRecomendados = recomendadorDePuntos.listadoDePuntosRecomendados(longitud,latitud,radio);

        for(PuntoRecomendado unPunto:listadoDePuntosRecomendados.puntosRecomendados){
            System.out.println(unPunto.longitud + ") " + unPunto.latitud);
        }

    }

}
