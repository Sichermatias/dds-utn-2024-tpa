package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import ar.edu.utn.frba.dds.dominio.archivos.carga_masiva.CampoInvalidoException;
import ar.edu.utn.frba.dds.dominio.archivos.carga_masiva.CargaMasiva;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
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
        Assertions.assertEquals(1, ColaboradorRepositorio.getInstancia().buscarTodos(Colaborador.class).size());
    }
    @Test
    void cargaMasivaDosNuevos() throws CampoInvalidoException {
        Mensajero mensajero = mock(Mensajero.class);
        doNothing().when(mensajero).enviarMensaje(any(Mensaje.class));
        CargaMasiva carga = new CargaMasiva(mensajero);
        String rutaArchivo =rutaBase + "CargaMasivaTest1.csv"; // Construye la ruta relativa al archivo
        carga.cargarArchivo(rutaArchivo, ";");
        Assertions.assertEquals(2, ColaboradorRepositorio.getInstancia().buscarTodos(Colaborador.class).size());
    }


    @Test
    void cargaMasivaErrorCampo() throws CampoInvalidoException {
        Mensajero mensajero = mock(Mensajero.class);
        doNothing().when(mensajero).enviarMensaje(any(Mensaje.class));
        CargaMasiva carga = new CargaMasiva(mensajero);
        String rutaArchivo = rutaBase + "CargaMasivaTest3.csv";
        carga.cargarArchivo(rutaArchivo, ";");
    }
}
