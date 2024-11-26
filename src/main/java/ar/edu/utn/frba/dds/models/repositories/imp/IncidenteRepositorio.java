package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class IncidenteRepositorio extends BaseRepositorio<Incidente> implements WithSimplePersistenceUnit {

    private static IncidenteRepositorio instancia;
    public static IncidenteRepositorio getInstancia(){
        if (instancia == null){
            instancia = new IncidenteRepositorio();
        }
        return instancia;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
