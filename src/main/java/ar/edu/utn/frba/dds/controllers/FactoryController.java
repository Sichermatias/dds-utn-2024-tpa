package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.models.repositories.imp.*;

public class FactoryController {

    public static Object controller(String nombre) {
        return switch (nombre) {
            case "Login" -> new LoginController(new UsuarioRepositorio());
            case "Registro" -> new RegistroUsuariosController(new ColaboradorRepositorio());
            case "Usuarios" -> new UsuariosController(new UsuarioRepositorio());
            case "LandingPage" -> new LandingPageController();
            case "Premios" -> new PremiosController(new ColaboradorRepositorio(), new PremioRepositorio(), new ColaboracionRepositorio());
            case "CargaMasiva" -> new CargaMasivaController();
            case "Colaboracion" -> ServiceLocator.instanceOf(ColaboracionController.class);
            case "Heladeras" -> new HeladerasController();
            case "Vulnerables" -> new RegistroVulnerableController(new PersonaVulnerableRepositorio(), ServiceLocator.instanceOf(ColaboracionController.class));
            case "FallosHeladera" -> new FallosHeladeraController(new FallosHeladeraRepositorio());
            case "ViandasHeladeraReporte" -> new ReporteViandasHeladeraController();
            case "ViandasDonadasColaborador" -> new ViandasDonadasColaboradorController(new ViandasDonadasColaboradorRepositorio());
            case "RecomendadorPuntosDonacion" -> new RecomendadorPuntosDonacionController();
            default -> null;
        };
    }
}
