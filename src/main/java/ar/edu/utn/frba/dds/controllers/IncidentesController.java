package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.VisitaIncidente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.*;

public class IncidentesController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private final IncidenteRepositorio incidenteRepositorio;
    ColaboradorRepositorio colaboradorRepositorio = ColaboradorRepositorio.getInstancia();

    public IncidentesController(IncidenteRepositorio incidenteRepositorio) {
        this.incidenteRepositorio = incidenteRepositorio;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        Long heladera_id = Long.valueOf(context.pathParam("id"));

        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        HeladerasRepositorio heladerasRepositorio = HeladerasRepositorio.getInstancia();
        Heladera heladera = heladerasRepositorio.buscarPorId(Heladera.class, heladera_id);

        IncidenteRepositorio repositorioIncidentes = IncidenteRepositorio.getInstancia();
        List<Incidente> incidentes = repositorioIncidentes.buscarPorHeladeraId(heladera_id);

        if (tipoRol != null) {
            model.put("incidentes", incidentes);
            model.put("heladera", heladera);
            context.render("incidentes/incidentes.hbs", model);
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

    public void listarVisitas(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long incidenteID = Long.valueOf(context.pathParam("incidenteID"));
        if(tipoRol == null || !this.tienePermisoDeVerVisitas(tipoRol)) {
            throw new AccessDeniedException();
        }
        List<VisitaIncidente> visitasIncidente = this.incidenteRepositorio.buscarVisitasPorIDIncidente(incidenteID);
        model.put("visitas", visitasIncidente);
        model.put("incidenteID", incidenteID);
        context.render("incidentes/visitas_de_incidente.hbs", model);
    }

    private boolean tienePermisoDeVerVisitas(String tipoRol) {
        return tipoRol.equals("ADMIN") ||
                tipoRol.equals("TECNICO") ||
                tipoRol.equals("COLABORADOR_JURIDICO") ||
                tipoRol.equals("COLABORADOR_HUMANO");
    }
}
