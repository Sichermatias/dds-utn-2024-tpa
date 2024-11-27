package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.persona.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class TecnicoRepositorio extends BaseRepositorio<Tecnico> implements WithSimplePersistenceUnit {

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
