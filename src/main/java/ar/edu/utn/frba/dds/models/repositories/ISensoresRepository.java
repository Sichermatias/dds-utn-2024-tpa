package ar.edu.utn.frba.dds.models.repositories;

import dominio.infraestructura.SensorDeMovimiento;
import dominio.infraestructura.SensorDeTemperatura;

import java.util.List;

public interface ISensoresRepository {
    SensorDeTemperatura buscarSensorTemperaturaPorIdHeladera(String idHeladera);

    void modificarSensorTemperatura(SensorDeTemperatura sensorTemperatura);

    SensorDeMovimiento buscarSensorMovimientoPorIdHeladera(String idHeladera);

    void modificarSensorMovimiento(SensorDeMovimiento sensorDeMovimiento);

    List<SensorDeTemperatura> buscarTodosLosSensores();
}
