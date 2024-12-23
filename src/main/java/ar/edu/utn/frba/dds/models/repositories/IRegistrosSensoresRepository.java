package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.dominio.infraestructura.RegistroSensor;

public interface IRegistrosSensoresRepository {
    void persistir(RegistroSensor registroSensor);
}
