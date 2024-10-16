package ar.edu.utn.frba.dds.models.entities;

import io.javalin.security.RouteRole;

public enum Rol implements RouteRole {
    ADMIN,
    COLABORADOR_HUMANO,
    COLABORADOR_JURIDICO,
    TECNICO,
}
