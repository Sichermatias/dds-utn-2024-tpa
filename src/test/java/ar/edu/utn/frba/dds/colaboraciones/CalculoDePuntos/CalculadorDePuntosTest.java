package ar.edu.utn.frba.dds.colaboraciones.CalculoDePuntos;

import dominio.colaboracion.*;
import dominio.infraestructura.Heladera;
import dominio.persona.CalculadorDePuntosAcumulados;
import dominio.persona.Colaborador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadorDePuntosTest {

    private Colaborador colaborador;
    private CalculadorDePuntosAcumulados calculador;

    @BeforeEach
    public void setUp() {
        colaborador = new Colaborador();
        calculador = new CalculadorDePuntosAcumulados();
        colaborador.setCalculadorDePuntos(calculador);
    }

    @Test
    public void testCalcularPuntosSinColaboraciones() {
        assertEquals(0.0, calculador.calcularPuntosDeColaborador(colaborador));
    }

    @Test
    public void testCalcularPuntosConUnaDonacionDeDinero() {
        DonacionDeDinero donacion = new DonacionDeDinero();
        donacion.setMonto(100.0);
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.cambiarTipoColaboracion(donacion);

        colaborador.agregarColaboracion(colaboracion);

        assertEquals(50.0, calculador.calcularPuntosDeColaborador(colaborador));  // 100 * 0.5
    }

    @Test
    public void testCalcularPuntosPorTarjetaEntregada() {
        RegistrarPersonaVulnerable registroPersonaVulnerable = new RegistrarPersonaVulnerable();
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.cambiarTipoColaboracion(registroPersonaVulnerable);

        colaborador.agregarColaboracion(colaboracion);

        assertEquals(2.0, calculador.calcularPuntosDeColaborador(colaborador));
    }

    @Test
    public void testCalcularPuntosConDistribucion() {
        Distribucion distribucion = new Distribucion();
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.cambiarTipoColaboracion(distribucion);

        colaborador.agregarColaboracion(colaboracion);

        assertEquals(1.0, calculador.calcularPuntosDeColaborador(colaborador));
    }

    @Test
    public void testCalcularPuntosConEncargarseDeHeladera() {
        EncargarseDeHeladera encargarseDeHeladera = new EncargarseDeHeladera();
        Colaborador colaborador = new Colaborador();
        Heladera heladera = new Heladera();
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-03-28")); //Debería calcular 2 meses hasta la fecha
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.cambiarTipoColaboracion(encargarseDeHeladera);
        encargarseDeHeladera.setHeladera(heladera);

        colaborador.agregarColaboracion(colaboracion);

        assertEquals(10.0, calculador.calcularPuntosDeColaborador(colaborador));  // 2 meses activa * 5


        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-12-28")); //5 meses de diferencia hasta ahora estando activa
        heladera.setActivo(false); //suma 5 meses al actualizar
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-04-28")); //Estuvo inactiva 1 mes, no suma nada
        assertEquals(25.0, calculador.calcularPuntosDeColaborador(colaborador)); // 5 meses activa * 5

        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-10-28")); //Deberia contar 7 meses pero sigue inactiva
        assertEquals(0.0, calculador.calcularPuntosDeColaborador(colaborador)); // 0 meses activa * 5

        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2024-01-28")); // 4 meses inactiva, no suma
        heladera.setActivo(true);
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.parse("2023-02-28"));
    }

    @Test
    public void testCalcularPuntosConMultiplesColaboraciones() {
        DonacionDeDinero donacion = new DonacionDeDinero();
        donacion.setMonto(100.0);
        RegistrarPersonaVulnerable registroPersonaVulnerable = new RegistrarPersonaVulnerable();
        Distribucion distribucion = new Distribucion();
        Oferta oferta = new Oferta();

        Colaboracion colaboracion1 = new Colaboracion();
        colaboracion1.cambiarTipoColaboracion(donacion);

        Colaboracion colaboracion2 = new Colaboracion();
        colaboracion2.cambiarTipoColaboracion(registroPersonaVulnerable);

        Colaboracion colaboracion3 = new Colaboracion();
        colaboracion3.cambiarTipoColaboracion(distribucion);

        Colaboracion colaboracion4 = new Colaboracion();
        colaboracion4.cambiarTipoColaboracion(oferta);

        colaborador.agregarColaboracion(colaboracion1);
        colaborador.agregarColaboracion(colaboracion2);
        colaborador.agregarColaboracion(colaboracion3);
        colaborador.agregarColaboracion(colaboracion4);

        assertEquals(53.0, calculador.calcularPuntosDeColaborador(colaborador));  // 50 + 2 + 1 + 0
    }
}
