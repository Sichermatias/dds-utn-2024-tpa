package dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.controllers.SensoresController;
import ar.edu.utn.frba.dds.models.repositories.IIncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.ISensoresRepository;
import dominio.infraestructura.SensorDeTemperatura;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class VerificarUltimaTemperaturaDeHeladeras implements Job {
    ISensoresRepository sensoresRepository;
    IIncidentesRepository incidentesRepository;
    SensoresController sensoresController = new SensoresController(sensoresRepository, incidentesRepository);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //Buscar todas las heladeras
        List<SensorDeTemperatura> sensoresTemperatura = sensoresRepository.buscarTodosLosSensores();
        //Por cada heladera, buscar el último registro de temperatura
        for (int i = 0; i < sensoresTemperatura.size(); i++) {
            if (sensoresTemperatura.get(i).ultimoRegistroSeCreoHaceMasDe(5)){
                //crear incidente
            }
        }
        //Si la ultima actualizacion fue hace mas de 5 minutos -> crear incidente
        //sensoresController.crearIncidente(new LocalDateTime(), );
    }

}
