package ar.edu.utn.frba.dds.dominio.persona.login;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMINISTRADOR,
    COLABORADOR_HUMANO,
    COLABORADOR_JURIDICO,
    TECNICO
}
