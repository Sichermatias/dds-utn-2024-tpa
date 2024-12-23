package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeMovimiento;
import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeTemperatura;
import ar.edu.utn.frba.dds.models.repositories.imp.*;

public class SensorService {
    SensoresTemperaturaRepository sensoresTemperaturaRepository;
    SensoresMovimientoRepository sensoresMovimientoRepository;

    public SensorService(){
        this.sensoresTemperaturaRepository = ServiceLocator.instanceOf(SensoresTemperaturaRepository.class);
        this.sensoresMovimientoRepository = ServiceLocator.instanceOf(SensoresMovimientoRepository.class);
    }

    public void crearSensores(Heladera heladera){
        SensorDeTemperatura sensorTemperatura = new SensorDeTemperatura();
        sensorTemperatura.setCodigo("Codigo");
        sensorTemperatura.setHeladera(heladera);
        sensoresTemperaturaRepository.persistir(sensorTemperatura);

        SensorDeMovimiento sensorMovimiento = new SensorDeMovimiento();
        sensorMovimiento.setCodigo("Codigo");
        sensorMovimiento.setHeladera(heladera);
        sensoresMovimientoRepository.persistir(sensorMovimiento);
    }
}
