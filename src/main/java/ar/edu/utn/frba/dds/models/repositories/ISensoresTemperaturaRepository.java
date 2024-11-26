package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeTemperatura;

import java.util.List;

public interface ISensoresTemperaturaRepository {
    SensorDeTemperatura buscarPorIdHeladera(Long idHeladera);

    void actualizar(SensorDeTemperatura sensorTemperatura);

    List<SensorDeTemperatura> buscarTodos();
}
