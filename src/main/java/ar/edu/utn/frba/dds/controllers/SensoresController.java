package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.infraestructura.*;
import ar.edu.utn.frba.dds.dtos.inputs.RegistroSensorMovDTO;
import ar.edu.utn.frba.dds.dtos.inputs.RegistroSensorTempDTO;
import ar.edu.utn.frba.dds.models.repositories.IIncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.IRegistrosSensoresRepository;
import ar.edu.utn.frba.dds.models.repositories.ISensoresMovimientoRepository;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.models.repositories.ISensoresTemperaturaRepository;
import ar.edu.utn.frba.dds.services.GestorDeIncidentesService;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;

public class SensoresController implements IMqttMessageListener {
    private final ISensoresTemperaturaRepository sensoresTemperaturaRepository;
    private final ISensoresMovimientoRepository sensoresMovimientoRepository;
    private final IIncidentesRepository incidentesRepository;
    private final IRegistrosSensoresRepository registrosSensoresRepository;
    private final GestorDeIncidentesService gestorDeIncidentesService;

    public SensoresController(
            ISensoresTemperaturaRepository sensoresTemperaturaRepository,
            ISensoresMovimientoRepository sensoresMovimientoRepository,
            IIncidentesRepository incidentesRepository,
            IRegistrosSensoresRepository registrosSensoresRepository, GestorDeIncidentesService gestorDeIncidentesService) {
        this.sensoresTemperaturaRepository = sensoresTemperaturaRepository;
        this.sensoresMovimientoRepository = sensoresMovimientoRepository;
        this.incidentesRepository = incidentesRepository;
        this.registrosSensoresRepository = registrosSensoresRepository;
        this.gestorDeIncidentesService = gestorDeIncidentesService;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        switch (topic) {
            case "dds2024/g12/heladeras/temperatura" -> {
                RegistroSensorTempDTO registroSensorTempDTO = new RegistroSensorTempDTO(mqttMessage);
                System.out.println("heladera: " + registroSensorTempDTO.getIdHeladera() + " temperatura: " + registroSensorTempDTO.getTemperatura());
                this.recibirDatoTemperatura(registroSensorTempDTO);
            }
            case "dds2024/g12/heladeras/movimiento" -> {
                RegistroSensorMovDTO registroSensorMovDTO = new RegistroSensorMovDTO(mqttMessage);
                this.recibirDatoMovimiento(registroSensorMovDTO);
            }
            default -> throw new Exception(); //TODO: revisar excepcion de mensaje sensor
        }
    }

    private void recibirDatoMovimiento(RegistroSensorMovDTO registroSensorMovDTO) {
        SensorDeMovimiento sensorDeMovimiento =
                this.sensoresMovimientoRepository.buscarPorIdHeladera(registroSensorMovDTO.getIdHeladera());

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        RegistroSensor registroSensor = SensorDeMovimiento.crearRegistro(fechaHoraActual);
        sensorDeMovimiento.agregarRegistro(registroSensor);

        Heladera heladeraDelSensor = sensorDeMovimiento.getHeladera();
        Incidente incidente = this.crearIncidente(
                fechaHoraActual,
                heladeraDelSensor,
                TipoIncidente.ALERTA_FRAUDE,
                this.descripcionAlertasMov(fechaHoraActual, heladeraDelSensor)
        );

        this.registrosSensoresRepository.persistir(registroSensor);

        this.incidentesRepository.agregar(incidente);

        this.sensoresMovimientoRepository.actualizarEstadoSensor(sensorDeMovimiento);

        this.gestorDeIncidentesService.gestionarIncidente(incidente);
    }

    private void recibirDatoTemperatura(RegistroSensorTempDTO registroSensorTempDTO) {
        Long heladeraID = registroSensorTempDTO.getIdHeladera();
        SensorDeTemperatura sensorTemperatura = this.sensoresTemperaturaRepository.buscarPorIdHeladera(heladeraID);

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        Double temperaturaCaptada = registroSensorTempDTO.getTemperatura();
        RegistroSensor registroSensor = SensorDeTemperatura.crearRegistro(temperaturaCaptada, fechaHoraActual);
        sensorTemperatura.agregarRegistro(registroSensor);
        this.registrosSensoresRepository.persistir(registroSensor);

        Heladera heladeraDelSensor = sensorTemperatura.getHeladera();
        EvaluadorTemperatura evaluadorTemperatura = sensorTemperatura.getEvaluadorTemperatura();
        if(evaluadorTemperatura.hayAlerta(temperaturaCaptada, heladeraDelSensor)) {
            Incidente incidente = this.crearIncidente(
                    fechaHoraActual,
                    heladeraDelSensor,
                    TipoIncidente.ALERTA_TEMPERATURA,
                    this.descripcionAlertasTemp(temperaturaCaptada, fechaHoraActual, heladeraDelSensor)
            );

            this.incidentesRepository.agregar(incidente);

            this.gestorDeIncidentesService.gestionarIncidente(incidente);
        }

        this.sensoresTemperaturaRepository.actualizar(sensorTemperatura);
    }

    private String descripcionAlertasMov(LocalDateTime fechaHoraActual, Heladera heladeraDelSensor) {
        return "Movimiento en la heladera \"" + heladeraDelSensor.getId() + "\" con fecha y hora "
                + fechaHoraActual.toString();
    }

    private String descripcionAlertasTemp(Double temperaturaCaptada, LocalDateTime fechaHoraActual, Heladera heladeraDelSensor) {
        return "Temperatura de " + temperaturaCaptada.toString() + "°C en la heladera \"" +
                heladeraDelSensor.getId() + "\" con fecha y hora " + fechaHoraActual.toString();
    }

    public Incidente crearIncidente(LocalDateTime fechaHora, Heladera heladeraDelSensor, TipoIncidente tipoIncidente, String descripcion) {
        Incidente incidente = new Incidente();
        incidente.setTipoIncidente(tipoIncidente);
        incidente.setDescripcionIncidente(descripcion);
        incidente.setFechaHoraAlta(fechaHora);
        incidente.setHeladeraIncidente(heladeraDelSensor);
        incidente.setAsignado(false);

        heladeraDelSensor.sumarCantIncidentes(1);
        heladeraDelSensor.setDesperfecto(true);

        return incidente;
    }
}
