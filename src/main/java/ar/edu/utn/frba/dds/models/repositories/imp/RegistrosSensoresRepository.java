package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.infraestructura.RegistroSensor;
import ar.edu.utn.frba.dds.models.repositories.IRegistrosSensoresRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class RegistrosSensoresRepository extends BaseRepositorio<RegistroSensor> implements IRegistrosSensoresRepository, WithSimplePersistenceUnit {
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
