package ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.services.ColaboracionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ActualizarDiasSinContarPuntajeHosteoHeladera implements Job {
    private final ColaboracionService colaboracionService;

    public ActualizarDiasSinContarPuntajeHosteoHeladera(ColaboracionService colaboracionService) {
        this.colaboracionService = colaboracionService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.colaboracionService.actualizarDiasSinAcumularPuntajeHosteoHeladera();
    }
}
