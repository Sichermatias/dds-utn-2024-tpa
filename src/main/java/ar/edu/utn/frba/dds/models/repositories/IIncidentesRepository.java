package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;

import java.util.List;

public interface IIncidentesRepository {
    void agregar(Incidente incidente);
    List<Incidente> buscarIncidentesSinAsignar();
    void actualizar(Incidente incidente);
}
