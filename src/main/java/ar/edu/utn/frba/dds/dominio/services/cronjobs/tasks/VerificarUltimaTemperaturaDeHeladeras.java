package ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.controllers.FactoryController;
import ar.edu.utn.frba.dds.controllers.SensoresController;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.models.repositories.IIncidentesRepository;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeTemperatura;
import ar.edu.utn.frba.dds.models.repositories.ISensoresTemperaturaRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.List;

public class VerificarUltimaTemperaturaDeHeladeras implements Job {
    ISensoresTemperaturaRepository sensoresTemperaturaRepository;
    IIncidentesRepository incidentesRepository;
    SensoresController sensoresController = (SensoresController) FactoryController.controller("Sensores");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Verificando ultima temperatura de heladeras");
        List<SensorDeTemperatura> sensoresTemperatura = sensoresTemperaturaRepository.buscarTodos();

        for (SensorDeTemperatura sensorDeTemperatura : sensoresTemperatura) {
            if (sensorDeTemperatura.ultimoRegistroSeCreoHaceMasDe(5)) {
                LocalDateTime fechaHoraActual = LocalDateTime.now();
                Heladera heladeraDelSensor = sensorDeTemperatura.getHeladera();
                String descripcion = "La heladera no ha enviado datos en los ultimos 5 minutos";
                Incidente incidente = sensoresController.crearIncidente(fechaHoraActual, heladeraDelSensor, TipoIncidente.ALERTA_FALLA_CONEXION, descripcion);
                incidente.setAsignado(false);
                this.incidentesRepository.agregar(incidente);
            }
        }
    }

}
