package ar.edu.utn.frba.dds.server.handlers;

import io.javalin.Javalin;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;

public class AccesDeniedHandler implements IHandler{

    @Override
    public void setHandle(Javalin app) {
    app.exception(AccessDeniedException.class, (e, ctx)-> {
       ctx.redirect("/");
       //             context.render("prohibido.hbs");
    });
    }
}
