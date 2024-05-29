package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import dominio.archivos.carga_masiva.CampoInvalidoException;
import dominio.archivos.carga_masiva.CargaMasiva;
import dominio.repositories.PersonaHumanaRepositorio;
import dominio.services.messageSender.Mensaje;
import dominio.services.messageSender.Mensajero;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;


public class CargaMasivaTests {
    String rutaBase = System.getProperty("user.dir")+ File.separator; // Obtiene el directorio base del proyecto

    @Test
    void cargaMasivaDosRepetidos() throws CampoInvalidoException{
        Mensajero mensajero = mock(Mensajero.class);
        doNothing().when(mensajero).enviarMensaje(any(Mensaje.class));
        CargaMasiva carga = new CargaMasiva(mensajero);
        String rutaArchivo=rutaBase+"CargaMasivaTest2.csv";
        carga.cargarArchivo(rutaArchivo,";");
        Assertions.assertEquals(1,PersonaHumanaRepositorio.obtenerInstancia().getPersonas().size());
    }
    @Test
    void cargaMasivaDosNuevos() throws CampoInvalidoException {
        Mensajero mensajero = mock(Mensajero.class);
        doNothing().when(mensajero).enviarMensaje(any(Mensaje.class));
        CargaMasiva carga = new CargaMasiva(mensajero);
        String rutaArchivo =rutaBase + "CargaMasivaTest1.csv"; // Construye la ruta relativa al archivo
        carga.cargarArchivo(rutaArchivo, ";");
        Assertions.assertEquals(2,PersonaHumanaRepositorio.obtenerInstancia().getPersonas().size());
    }


    @Test
    void cargaMasivaErrorCampo() {
        Mensajero mensajero = mock(Mensajero.class);
        doNothing().when(mensajero).enviarMensaje(any(Mensaje.class));
        CargaMasiva carga = new CargaMasiva(mensajero);
        String rutaArchivo = rutaBase + "CargaMasivaTest3.csv";
        CampoInvalidoException exception = assertThrows(CampoInvalidoException.class, () -> {
            carga.cargarArchivo(rutaArchivo, ";");
        });
    }
}
