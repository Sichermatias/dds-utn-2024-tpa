package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeMovimiento;
import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeTemperatura;

import java.util.List;

public interface ISensoresRepository {
    SensorDeTemperatura buscarSensorTemperaturaPorIdHeladera(String idHeladera);

    void modificarSensorTemperatura(SensorDeTemperatura sensorTemperatura);

    SensorDeMovimiento buscarSensorMovimientoPorIdHeladera(String idHeladera);

    void modificarSensorMovimiento(SensorDeMovimiento sensorDeMovimiento);

    List<SensorDeTemperatura> buscarTodosLosSensores();
}
