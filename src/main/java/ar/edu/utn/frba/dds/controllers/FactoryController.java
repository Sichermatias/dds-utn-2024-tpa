package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.services.messageSender.strategies.EstrategiaMensaje;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;

public class FactoryController {

    public static Object controller(String nombre) {
        return switch (nombre) {
            case "Login" -> new LoginController(new UsuarioRepositorio());
            case "Registro" -> new RegistroController(new ColaboradorRepositorio());
            case "Usuarios" -> new UsuariosController(new UsuarioRepositorio());
            case "LandingPage" -> new LandingPageController();
            case "Premios" -> new PremiosController(new ColaboradorRepositorio());
            case "CargaMasiva" -> new CargaMasivaController();
            case "Colaboracion" -> new ColaboracionController();
            default -> null;
        };
    }
}
