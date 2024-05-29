package ar.edu.utn.frba.dds.colaboraciones.CalculoDePuntos;

import dominio.colaboracion.Colaboracion;
import dominio.colaboracion.EncargarseDeHeladera;
import dominio.infraestructura.Heladera;
import dominio.persona.CalculadorDePuntosAcumulados;
import dominio.persona.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculoDePuntosHeladeraTest {

    private Persona persona;
    private CalculadorDePuntosAcumulados calculador;
    private Heladera heladera;

    @BeforeEach
    public void setUp() {
        persona = new Persona();
        calculador = new CalculadorDePuntosAcumulados();
        persona.setCalculadorDePuntos(calculador);
        EncargarseDeHeladera encargarseDeHeladera = new EncargarseDeHeladera();
        heladera = new Heladera();
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.cambiarTipoColaboracion(encargarseDeHeladera);
        encargarseDeHeladera.setHeladera(heladera);
        persona.agregarColaboracion(colaboracion);
    }

    @Test
    public void testDosMesesActiva() {
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2024-03-28")); //Debería calcular 2 meses hasta la fecha

        assertEquals(10.0, calculador.calcularPuntosDeColaborador(persona)); // 2 meses activa * 5
    }

    @Test
    public void cincoMesesActivaYUnoInactiva() {
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-12-28")); //5 meses de diferencia hasta ahora estando activa
        heladera.setActivo(false); //suma 5 meses al actualizar
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-04-28")); //Estuvo inactiva 1 mes, no suma nada

        assertEquals(25.0, calculador.calcularPuntosDeColaborador(persona)); // 5 meses activa * 5
    }

    @Test
    public void sieteMesesInactiva() {
        heladera.setActivo(false);
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-10-28")); //Deberia contar 7 meses pero está inactiva

        assertEquals(0.0, calculador.calcularPuntosDeColaborador(persona)); // 0 meses activa * 5
    }

    @Test
    public void cuatroMesesInactivaYTresActiva() {
        heladera.setActivo(false);
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2024-01-28")); // 4 meses inactiva, no suma
        heladera.setActivo(true);
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2024-02-28")); // 3 meses activa, suma

        assertEquals(15.0, calculador.calcularPuntosDeColaborador(persona)); // 3 meses activa * 5
    }
}
