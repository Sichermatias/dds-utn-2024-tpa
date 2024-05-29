package ar.edu.utn.frba.dds;

import static org.mockito.Mockito.*;
import dominio.archivos.carga_masiva.CampoInvalidoException;
import dominio.archivos.carga_masiva.CargaMasiva;
import dominio.persona.verificadorContrasenias.Complejidad;
import dominio.persona.verificadorContrasenias.Longitud;
import dominio.persona.verificadorContrasenias.Requisitos;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class CargaMasivaTests {
    CargaMasiva carga = new CargaMasiva();
    String rutaBase = System.getProperty("user.dir")+ File.separator; // Obtiene el directorio base del proyecto

    @Test
    void cargaMasivaDosNuevos() throws CampoInvalidoException {
        String rutaArchivo =rutaBase + "CargaMasivaTest1.csv"; // Construye la ruta relativa al archivo
        carga.cargarArchivo(rutaArchivo, ";");
    }
    @Test
    void cargaMasivaDosRepetidos() throws CampoInvalidoException{
        String rutaArchivo=rutaBase+"CargaMasivaTest2.csv";
        carga.cargarArchivo(rutaArchivo,";");
    }

    @Test
    void cargaMasivaErrorCampo() throws CampoInvalidoException{
        String rutaArchivo=rutaBase+"CargaMasivaTest3.csv";
        carga.cargarArchivo(rutaArchivo,";");
    }
}
