package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.incidentes.VisitaIncidente;
import ar.edu.utn.frba.dds.dominio.persona.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class TecnicoRepositorio extends BaseRepositorio<Tecnico> implements WithSimplePersistenceUnit {

    private static TecnicoRepositorio instancia;
    public static TecnicoRepositorio getInstancia(){
        if (instancia == null){
            instancia = new TecnicoRepositorio();
        }
        return instancia;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
