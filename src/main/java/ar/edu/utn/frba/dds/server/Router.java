package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.FactoryController;
import ar.edu.utn.frba.dds.controllers.LoginController;
import ar.edu.utn.frba.dds.controllers.RegistroController;
import ar.edu.utn.frba.dds.controllers.UsuariosController;
import io.javalin.Javalin;

public class Router {
    public static void init(Javalin app) {
        // Pantalla principal y login
        app.get("/", ServiceLocator.instanceOf(LoginController.class)::index);
        app.get("/login", ((LoginController) FactoryController.controller("Login"))::index);
        app.post("/login", ((LoginController) FactoryController.controller("Login"))::update);

        // Registro de personas
        app.get("/registro", ((RegistroController) FactoryController.controller("Registro"))::elegirTipo);
        app.get("/registro/juridica", ((RegistroController) FactoryController.controller("Registro"))::formularioJuridica);
        app.get("/registro/humana", ((RegistroController) FactoryController.controller("Registro"))::formularioHumana);
        app.post("/registro/juridica", ((RegistroController) FactoryController.controller("Registro"))::registrarJuridica);
        app.post("/registro/humana", ((RegistroController) FactoryController.controller("Registro"))::registrarHumana);

        // Lógica de usuarios (CRUD)
        app.get("/usuarios", ((UsuariosController) FactoryController.controller("Usuarios"))::index);
        app.post("/usuarios/crear", ((UsuariosController) FactoryController.controller("Usuarios"))::create);
        app.get("/usuarios/{id}/eliminar", ((UsuariosController) FactoryController.controller("Usuarios"))::delete);
        app.get("/usuarios/{id}", ((UsuariosController) FactoryController.controller("Usuarios"))::show);
        app.post("/usuarios/{id}/editar", ((UsuariosController) FactoryController.controller("Usuarios"))::edit);
    };
}
