package ar.edu.utn.frba.dds;

import dominio.services.recomendadorDePuntos.RecomendadorDePuntosAdapter;
import dominio.services.recomendadorDePuntos.RecomendadorDePuntos;
import dominio.calculadorDePuntos.PuntoRecomendado;
import dominio.calculadorDePuntos.ListadoDePuntosRecomendados;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
