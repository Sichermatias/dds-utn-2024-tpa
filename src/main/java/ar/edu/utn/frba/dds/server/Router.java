package ar.edu.utn.frba.dds.server;
import ar.edu.utn.frba.dds.controllers.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.controllers.*;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Router implements WithSimplePersistenceUnit {

    public void init() {

        Server.app().routes(() -> {
            get("/", ((LandingPageController) FactoryController.controller("LandingPage"))::index);
            get("login", ((LoginController) FactoryController.controller("Login"))::index);
            post("login", ((LoginController) FactoryController.controller("Login"))::update);

            get("usuarios", ((UsuariosController) FactoryController.controller("Usuarios"))::index);
            get("usuarios/admin", ((UsuariosController) FactoryController.controller("Usuarios"))::update);

            post("usuarios/crear", ((UsuariosController) FactoryController.controller("Usuarios"))::save);
            get("usuarios/{id}/eliminar", ((UsuariosController) FactoryController.controller("Usuarios"))::delete);
            get("usuarios/{id}", ((UsuariosController) FactoryController.controller("Usuarios"))::show);
            post("usuarios/{id}/editar", ((UsuariosController) FactoryController.controller("Usuarios"))::edit);

            after(x->{
                entityManager().clear();
            });
        });
    }
}
