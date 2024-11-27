package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.services.GestorDeIncidentesService;

public class FactoryController {

    public static Object controller(String nombre) {
        return switch (nombre) {
            case "Login" -> new LoginController(new UsuarioRepositorio());
            case "Registro" -> new RegistroUsuariosController(new ColaboradorRepositorio(),new LocalidadRepositorio());
            case "Usuarios" -> new UsuariosController(new UsuarioRepositorio());
            case "LandingPage" -> new LandingPageController();
            case "Tecnicos" -> new TecnicosController(new ColaboradorRepositorio(),new LocalidadRepositorio() );
            case "Premios" -> new PremiosController(new ColaboradorRepositorio(), new PremioRepositorio(), new ColaboracionRepositorio());
            case "PremioCanjes" -> new PremioCanjesController(new ColaboradorRepositorio(), new PremioRepositorio(), new PremioCanjeRepositorio());

            case "Colaboracion" -> ServiceLocator.instanceOf(ColaboracionController.class);

            case "Heladeras" -> new HeladerasController();
            case "FallosHeladera" -> new FallosHeladeraController(new FallosHeladeraRepositorio());
            case "Incidentes" -> new IncidentesController();

            case "CargaMasiva" -> new CargaMasivaController();
            case "Vulnerables" -> new RegistroVulnerableController(new PersonaVulnerableRepositorio(), ServiceLocator.instanceOf(ColaboracionController.class));
            case "ViandasHeladeraReporte" -> new ReporteViandasHeladeraController();
            case "ViandasDonadasColaborador" -> new ViandasDonadasColaboradorController(new ViandasDonadasColaboradorRepositorio());
            case "RecomendadorPuntosDonacion" -> new RecomendadorPuntosDonacionController();
            case "RecomendadorPuntos" -> new RecomendadorPuntosController();

            case "Sensores" -> new SensoresController(new SensoresTemperaturaRepository(), new SensoresMovimientoRepository(), new IncidentesRepository(), new RegistrosSensoresRepository(), ServiceLocator.instanceOf(GestorDeIncidentesService.class));
            case "Apertura" -> new AperturaController(new PedidoDeAperturaRepositorio(), new ColaboradorRepositorio());
            default -> null;
        };
    }
}
