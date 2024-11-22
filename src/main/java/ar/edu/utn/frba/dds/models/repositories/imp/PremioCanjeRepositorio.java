package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.colaboracion.PremioCanje;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class PremioCanjeRepositorio extends BaseRepositorio<PremioCanje> implements WithSimplePersistenceUnit {
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
