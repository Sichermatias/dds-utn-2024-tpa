package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeMovimiento;

public interface ISensoresMovimientoRepository {

    SensorDeMovimiento buscarPorIdHeladera(String idHeladera);

    void actualizarEstadoSensor(SensorDeMovimiento sensorDeMovimiento);
}
