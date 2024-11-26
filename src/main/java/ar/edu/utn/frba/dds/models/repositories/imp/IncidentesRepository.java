package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.repositories.IIncidentesRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class IncidentesRepository extends BaseRepositorio<Incidente> implements IIncidentesRepository, WithSimplePersistenceUnit {
    @Override
    public void agregar(Incidente incidente) {
        super.persistir(incidente);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    @Override
    public List<Incidente> buscarIncidentesSinAsignar() {
        return getEntityManager()
                .createQuery("SELECT i FROM Incidente i WHERE i.asignado = false OR i.asignado IS NULL", Incidente.class)
                .getResultList();
    }
}
