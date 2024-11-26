package ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.services.GestorDeIncidentesService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AsignarIncidentesATecnicos implements Job {
    private final GestorDeIncidentesService gestorDeIncidentesService;

    public AsignarIncidentesATecnicos(GestorDeIncidentesService gestorDeIncidentesService) {
        this.gestorDeIncidentesService = gestorDeIncidentesService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.gestorDeIncidentesService.gestionarIncidentes();
    }
}
