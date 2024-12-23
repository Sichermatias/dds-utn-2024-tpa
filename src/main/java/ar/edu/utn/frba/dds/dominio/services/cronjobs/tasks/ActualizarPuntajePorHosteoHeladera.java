package ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.services.ColaboracionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ActualizarPuntajePorHosteoHeladera implements Job {
    private final ColaboracionService colaboracionService;

    public ActualizarPuntajePorHosteoHeladera(ColaboracionService colaboracionService) {
        this.colaboracionService = colaboracionService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.colaboracionService.actualizarPuntajeColaboradoresPorHosteoHeladera();
    }
}
