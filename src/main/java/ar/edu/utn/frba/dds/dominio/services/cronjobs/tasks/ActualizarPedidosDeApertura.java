package ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.services.PedidoDeAperturaService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ActualizarPedidosDeApertura implements Job {
    private final PedidoDeAperturaService pedidoDeAperturaService;

    public ActualizarPedidosDeApertura(PedidoDeAperturaService pedidoDeAperturaService) {
        this.pedidoDeAperturaService = pedidoDeAperturaService;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.pedidoDeAperturaService.actualizarPedidosDeApertura();
    }
}
