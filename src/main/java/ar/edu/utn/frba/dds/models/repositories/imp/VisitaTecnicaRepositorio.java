package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.incidentes.VisitaIncidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class VisitaTecnicaRepositorio extends BaseRepositorio<VisitaIncidente> implements WithSimplePersistenceUnit {

    private static VisitaTecnicaRepositorio instancia;
    public static VisitaTecnicaRepositorio getInstancia(){
        if (instancia == null){
            instancia = new VisitaTecnicaRepositorio();
        }
        return instancia;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
