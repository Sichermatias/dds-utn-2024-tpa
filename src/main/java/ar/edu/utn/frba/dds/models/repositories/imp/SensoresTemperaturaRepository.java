package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeTemperatura;
import ar.edu.utn.frba.dds.models.repositories.ISensoresTemperaturaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class SensoresTemperaturaRepository extends BaseRepositorio<SensorDeTemperatura> implements ISensoresTemperaturaRepository, WithSimplePersistenceUnit {
    @Override
    public SensorDeTemperatura buscarPorIdHeladera(Long idHeladera) {
        return (SensorDeTemperatura) super.buscarPorHeladeraId(SensorDeTemperatura.class, idHeladera);
    }

    @Override
    public void actualizar(SensorDeTemperatura sensorTemperatura) {
        super.actualizar(sensorTemperatura);
    }

    @Override
    public List<SensorDeTemperatura> buscarTodos() {
        return super.buscarTodos(SensorDeTemperatura.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
