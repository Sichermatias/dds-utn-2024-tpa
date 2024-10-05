package ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.controllers.SensoresController;
import ar.edu.utn.frba.dds.models.repositories.IIncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.ISensoresRepository;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.infraestructura.SensorDeTemperatura;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.List;

public class VerificarUltimaTemperaturaDeHeladeras implements Job {
    ISensoresRepository sensoresRepository;
    IIncidentesRepository incidentesRepository;
    SensoresController sensoresController = new SensoresController(sensoresRepository, incidentesRepository);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Verificando ultima temperatura de heladeras");
        List<SensorDeTemperatura> sensoresTemperatura = sensoresRepository.buscarTodosLosSensores();

        for (int i = 0; i < sensoresTemperatura.size(); i++) {
            if (sensoresTemperatura.get(i).ultimoRegistroSeCreoHaceMasDe(5)){
                LocalDateTime fechaHoraActual = LocalDateTime.now();
                Heladera heladeraDelSensor = sensoresTemperatura.get(i).getHeladera();
                String descripcion = "La heladera no ha enviado datos en los ultimos 5 minutos";
                sensoresController.crearIncidente(fechaHoraActual, heladeraDelSensor, descripcion);
            }
        }
    }

}
