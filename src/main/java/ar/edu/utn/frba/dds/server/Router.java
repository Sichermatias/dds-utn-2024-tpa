package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.*;
import io.javalin.Javalin;

public class Router {
    public static void init(Javalin app) {
        // Pantalla principal y login
        //app.get("/", ServiceLocator.instanceOf(LandingPageController.class)::index);
        app.get("/", ((LandingPageController) FactoryController.controller("LandingPage"))::index);
        app.get("/login", ((LoginController) FactoryController.controller("Login"))::index);
        app.post("/login", ((LoginController) FactoryController.controller("Login"))::update);
        app.get("/logout", ((LoginController) FactoryController.controller("Login"))::logout);

        // Registro de personas
        app.get("/registro", ((RegistroController) FactoryController.controller("Registro"))::elegirTipo);
        app.get("/registro/juridica", ((RegistroController) FactoryController.controller("Registro"))::formularioJuridica);
        app.get("/registro/humana", ((RegistroController) FactoryController.controller("Registro"))::formularioHumana);
        app.post("/registro/juridica", ((RegistroController) FactoryController.controller("Registro"))::registrarJuridica);
        app.post("/registro/humana", ((RegistroController) FactoryController.controller("Registro"))::registrarHumana);
        app.get("/check-username", ((RegistroController) FactoryController.controller("Registro"))::checkUsername);
        app.get("/registro/vulnerable", ((RegistroController) FactoryController.controller("Registro"))::indexRegistroVulnerable);
        app.post("/registro/vulnerable", ((RegistroController) FactoryController.controller("Registro"))::registroVulnerable);


        app.get("/cargamasiva", ((CargaMasivaController) FactoryController.controller("CargaMasiva"))::index);
        app.post("/cargar-csv", ((CargaMasivaController) FactoryController.controller("CargaMasiva"))::cargarCSV);
        app.get("/nosotros", ((UsuariosController) FactoryController.controller("Usuarios"))::nosotros);

        app.get("/mapa", ((LandingPageController) FactoryController.controller("LandingPage"))::index);

        // Lógica de usuarios (CRUD)
        app.get("/usuarios", ((UsuariosController) FactoryController.controller("Usuarios"))::index);
        app.post("/usuarios/crear", ((UsuariosController) FactoryController.controller("Usuarios"))::create);
        app.get("/usuarios/{id}/eliminar", ((UsuariosController) FactoryController.controller("Usuarios"))::delete);
        app.get("/usuarios/{id}", ((UsuariosController) FactoryController.controller("Usuarios"))::show);
        app.post("/usuarios/{id}/editar", ((UsuariosController) FactoryController.controller("Usuarios"))::edit);

        //Puntos y canje de premios
        app.get("/puntos-y-premios", ((PremiosController) FactoryController.controller("Premios"))::index);
    }
}
