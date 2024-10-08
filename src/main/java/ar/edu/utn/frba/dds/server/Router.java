package ar.edu.utn.frba.dds.server;
import ar.edu.utn.frba.dds.controllers.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Router implements WithSimplePersistenceUnit {
    public void init() {
        Server.app().routes(() -> {

            // Pantalla principal y login
            get("/", ((LandingPageController) FactoryController.controller("LandingPage"))::index);
            get("/login", ((LoginController) FactoryController.controller("Login"))::index);
            post("/login", ((LoginController) FactoryController.controller("Login"))::update);

            // Registro de personas
            get("/registro", ((RegistroController) FactoryController.controller("Registro"))::elegirTipo);
            get("/registro/juridica", ((RegistroController) FactoryController.controller("Registro"))::formularioJuridica);
            get("/registro/humana", ((RegistroController) FactoryController.controller("Registro"))::formularioHumana);
            post("/registro/juridica", ((RegistroController) FactoryController.controller("Registro"))::registrarJuridica);
            post("/registro/humana", ((RegistroController) FactoryController.controller("Registro"))::registrarHumana);

            // Lógica de usuarios (CRUD)
            get("/usuarios", ((UsuariosController) FactoryController.controller("Usuarios"))::index);
            post("/usuarios/crear", ((UsuariosController) FactoryController.controller("Usuarios"))::create);
            get("/usuarios/{id}/eliminar", ((UsuariosController) FactoryController.controller("Usuarios"))::delete);
            get("/usuarios/{id}", ((UsuariosController) FactoryController.controller("Usuarios"))::show);
            post("/usuarios/{id}/editar", ((UsuariosController) FactoryController.controller("Usuarios"))::edit);

            // Limpiar la sesión al finalizar cada petición
            after(ctx -> {
                entityManager().clear();
            });
        });
    }
}
