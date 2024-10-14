package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;

public class FactoryController {

    public static Object controller(String nombre) {
        Object controller = null;
        switch (nombre) {
            case "Login": controller = new LoginController(new UsuarioRepositorio()); break;
            case "Registro": controller= new RegistroController(new ColaboradorRepositorio());break;
            case "Usuarios": controller = new UsuariosController(new UsuarioRepositorio()); break;
            case "LandingPage": controller = new LandingPageController();break;
        }
        return controller;
    }
}
