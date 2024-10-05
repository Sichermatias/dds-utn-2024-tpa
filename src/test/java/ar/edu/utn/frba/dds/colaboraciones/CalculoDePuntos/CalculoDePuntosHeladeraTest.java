/*package ar.edu.utn.frba.dds.colaboraciones.CalculoDePuntos;

import ar.edu.utn.frba.dds.colaboracion.dominio.Colaboracion;
import ar.edu.utn.frba.dds.colaboracion.dominio.HostearHeladera;
import ar.edu.utn.frba.dds.infraestructura.dominio.Heladera;
import dominio.persona.CalculadorDePuntosAcumulados;
import ar.edu.utn.frba.dds.persona.dominio.Colaborador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculoDePuntosHeladeraTest {

    private Colaborador colaborador;
    private CalculadorDePuntosAcumulados calculador;
    private Heladera heladera;

    @BeforeEach
    public void setUp() {
        colaborador = new Colaborador();
        calculador = new CalculadorDePuntosAcumulados();
        colaborador.setCalculadorDePuntos(calculador);
        HostearHeladera hostearHeladera = new HostearHeladera();
        heladera = new Heladera();
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.cambiarTipoColaboracion(hostearHeladera);
        hostearHeladera.setHeladera(heladera);
        colaborador.agregarColaboracion(colaboracion);
    }

    @Test
    public void testDosMesesActiva() {
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2024-03-28")); //Debería calcular 2 meses hasta la fecha

        assertEquals(10.0, calculador.calcularPuntosDeColaborador(colaborador)); // 2 meses activa * 5
    }

    @Test
    public void cincoMesesActivaYUnoInactiva() {
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-12-28")); //5 meses de diferencia hasta ahora estando activa
        heladera.setActivo(false); //suma 5 meses al actualizar
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-04-28")); //Estuvo inactiva 1 mes, no suma nada

        assertEquals(25.0, calculador.calcularPuntosDeColaborador(colaborador)); // 5 meses activa * 5
    }

    @Test
    public void sieteMesesInactiva() {
        heladera.setActivo(false);
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-10-28")); //Deberia contar 7 meses pero está inactiva

        assertEquals(0.0, calculador.calcularPuntosDeColaborador(colaborador)); // 0 meses activa * 5
    }

    @Test
    public void cuatroMesesInactivaYTresActiva() {
        heladera.setActivo(false);
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2024-01-28")); // 4 meses inactiva, no suma
        heladera.setActivo(true);
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2024-02-28")); // 3 meses activa, suma

        assertEquals(15.0, calculador.calcularPuntosDeColaborador(colaborador)); // 3 meses activa * 5
    }
}
*/