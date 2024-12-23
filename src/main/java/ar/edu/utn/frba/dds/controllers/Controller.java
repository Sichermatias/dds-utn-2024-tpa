package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.UsuarioRepositorio;
import io.javalin.http.Context;

public abstract class Controller {
    private final UsuarioRepositorio usuarioRepositorio = ServiceLocator.instanceOf(UsuarioRepositorio.class);

    protected Usuario usuarioLogueado(Context ctx) {
        if(ctx.sessionAttribute("usuario_id") == null)
            return null;

        Long idUsuario = ctx.sessionAttribute("usuario_id");
        return usuarioRepositorio.buscarPorId(Usuario.class, idUsuario);
    }
}
