package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.inputs.RegistroSensorMovDTO;
import ar.edu.utn.frba.dds.dtos.inputs.RegistroSensorTempDTO;
import ar.edu.utn.frba.dds.models.repositories.IIncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.ISensoresRepository;
import dominio.incidentes.GestorDeIncidentes;
import dominio.incidentes.Incidente;
import dominio.incidentes.TipoIncidente;
import dominio.infraestructura.EvaluadorTemperatura;
import dominio.infraestructura.Heladera;
import dominio.infraestructura.SensorDeMovimiento;
import dominio.infraestructura.SensorDeTemperatura;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;

public class SensoresController implements IMqttMessageListener {
    private final ISensoresRepository sensoresRepository;
    private final IIncidentesRepository incidentesRepository;

    public SensoresController(
            ISensoresRepository sensoresRepository,
            IIncidentesRepository incidentesRepository
    ) {
        this.sensoresRepository = sensoresRepository;
        this.incidentesRepository = incidentesRepository;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        switch (topic) {
            case "dds2024/g12/heladeras/temperatura" -> {
                RegistroSensorTempDTO registroSensorTempDTO = new RegistroSensorTempDTO(mqttMessage);
                this.recibirDatoTemperatura(registroSensorTempDTO);
            }
            case "dds2024/g12/heladeras/movimiento" -> {
                RegistroSensorMovDTO registroSensorMovDTO = new RegistroSensorMovDTO(mqttMessage);
                this.recibirDatoMovimiento(registroSensorMovDTO);
            }
            case default -> throw new Exception(); //TODO 2024-07-03: revisar excepcion de mensaje sensor
        }
    }

    private void recibirDatoMovimiento(RegistroSensorMovDTO registroSensorMovDTO) {
        SensorDeMovimiento sensorDeMovimiento =
                this.sensoresRepository.buscarSensorMovimientoPorIdHeladera(registroSensorMovDTO.getIdHeladera());

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        sensorDeMovimiento.agregarRegistro(fechaHoraActual);

        Heladera heladeraDelSensor = sensorDeMovimiento.getHeladera();
        this.crearIncidente(fechaHoraActual, heladeraDelSensor,
                this.descripcionAlertasMov(fechaHoraActual, heladeraDelSensor));

        this.sensoresRepository.modificarSensorMovimiento(sensorDeMovimiento);
    }

    private void recibirDatoTemperatura(RegistroSensorTempDTO registroSensorTempDTO) {
        SensorDeTemperatura sensorTemperatura =
                this.sensoresRepository.buscarSensorTemperaturaPorIdHeladera(registroSensorTempDTO.getIdHeladera());

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        Double temperaturaCaptada = registroSensorTempDTO.getTemperatura();
        sensorTemperatura.agregarRegistro(temperaturaCaptada, fechaHoraActual);

        Heladera heladeraDelSensor = sensorTemperatura.getHeladera();
        EvaluadorTemperatura evaluadorTemperatura = sensorTemperatura.getEvaluadorTemperatura();
        if(evaluadorTemperatura.hayAlerta(temperaturaCaptada, heladeraDelSensor)) {
            this.crearIncidente(fechaHoraActual, heladeraDelSensor,
                    this.descripcionAlertasTemp(temperaturaCaptada, fechaHoraActual, heladeraDelSensor));
        }

        this.sensoresRepository.modificarSensorTemperatura(sensorTemperatura); //TODO 2024-07-03: averiguar si persiste en cascada. Sino, modificar agregarIncidenteAHeladera
    }

    private String descripcionAlertasMov(LocalDateTime fechaHoraActual, Heladera heladeraDelSensor) {
        return "Movimiento en la heladera \"" + heladeraDelSensor.getId() + "\" con fecha y hora "
                + fechaHoraActual.toString();
    }

    private String descripcionAlertasTemp(Double temperaturaCaptada, LocalDateTime fechaHoraActual, Heladera heladeraDelSensor) {
        return "Temperatura de " + temperaturaCaptada.toString() + "°C en la heladera \"" +
                heladeraDelSensor.getId() + "\" con fecha y hora " + fechaHoraActual.toString();
    }

    private void crearIncidente(LocalDateTime fechaHora, Heladera heladeraDelSensor, String descripcion) {
        Incidente incidente = new Incidente();
        incidente.setTipoIncidente(TipoIncidente.ALERTA);
        incidente.setDescripcionIncidente(descripcion);
        incidente.setFechaIncidente(fechaHora);
        incidente.setHeladeraIncidente(heladeraDelSensor);

        this.agregarIncidenteAHeladera(heladeraDelSensor);

        //TODO 2024-07-03: Persistir de alguna manera el tecnico asignado
        GestorDeIncidentes.getInstanciaGestor().gestionarIncidente(incidente);

        this.incidentesRepository.agregar(incidente);
    }

    private void agregarIncidenteAHeladera(Heladera heladeraDelSensor) {
        int cantSemanalIncidentes = heladeraDelSensor.getCantSemanalIncidentes();
        cantSemanalIncidentes++;
        heladeraDelSensor.setCantSemanalIncidentes(cantSemanalIncidentes);
    }
}
