package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.dominio.infraestructura.FiltroSuscripcion;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.infraestructura.Suscripcion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.reportes.FallosPorHeladera;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.services.messageSender.strategies.EstrategiaMail;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.services.GestorDeIncidentesService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class IncidentesController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    ColaboradorRepositorio colaboradorRepositorio = ColaboradorRepositorio.getInstancia();

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        Long heladera_id = Long.valueOf(context.pathParam("id"));

        IncidenteRepositorio repositorioIncidentes = IncidenteRepositorio.getInstancia();
        List<Incidente> incidentes = repositorioIncidentes.buscarPorHeladeraId(heladera_id);

        if (tipoRol != null) {
            model.put("incidentes", incidentes);
            context.render("/incidentes/incidentes.hbs", model);
        }
        else context.redirect("/login");
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
