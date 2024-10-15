package ar.edu.utn.frba.dds.dominio.persona.login;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMINI,
    COLABORADOR,
    TECNICO
}
