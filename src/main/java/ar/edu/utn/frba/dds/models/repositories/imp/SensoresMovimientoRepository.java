package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeMovimiento;
import ar.edu.utn.frba.dds.models.repositories.ISensoresMovimientoRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class SensoresMovimientoRepository extends BaseRepositorio<SensorDeMovimiento> implements ISensoresMovimientoRepository, WithSimplePersistenceUnit {

    @Override
    public SensorDeMovimiento buscarPorIdHeladera(Long idHeladera) {
        return super.buscarPorHeladeraId(SensorDeMovimiento.class, idHeladera).get(0);
    }

    @Override
    public void actualizarEstadoSensor(SensorDeMovimiento sensorDeMovimiento) {
        super.actualizar(sensorDeMovimiento);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
