package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.FallosHeladeraRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.PersonaVulnerableRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;

public class FactoryController {

    public static Object controller(String nombre) {
        return switch (nombre) {
            case "Login" -> new LoginController(new UsuarioRepositorio());
            case "Registro" -> new RegistroUsuariosController(new ColaboradorRepositorio());
            case "Usuarios" -> new UsuariosController(new UsuarioRepositorio());
            case "LandingPage" -> new LandingPageController();
            case "Premios" -> new PremiosController(new ColaboradorRepositorio());
            case "CargaMasiva" -> new CargaMasivaController();
            case "Colaboracion" -> new ColaboracionController();
            case "Heladeras" -> new HeladerasController();
            case "Vulnerable" -> new RegistroVulnerableController(new PersonaVulnerableRepositorio());
            case "Fallas" -> new FallasTecnicasController();
            case "FallosHeladera" -> new FallosHeladeraController(new FallosHeladeraRepositorio());
            default -> null;
        };
    }
}
