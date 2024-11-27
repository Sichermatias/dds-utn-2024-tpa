package ar.edu.utn.frba.dds.dominio.services.cronjobs;

import ar.edu.utn.frba.dds.controllers.FactoryController;
import ar.edu.utn.frba.dds.controllers.SensoresController;
import ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks.*;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.services.ColaboracionService;
import ar.edu.utn.frba.dds.services.GestorDeIncidentesService;
import ar.edu.utn.frba.dds.services.TransaccionService;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CrontasksScheduler {
    public void start() {
        try {
            GenericJobFactory jobFactory = new GenericJobFactory();

            jobFactory.registerJob(
                    VerificarUltimaTemperaturaDeHeladeras.class,
                    new VerificarUltimaTemperaturaDeHeladeras(
                            new SensoresTemperaturaRepository(),
                            new IncidentesRepository(),
                            (SensoresController) FactoryController.controller("Sensores")
                    )
            );

            jobFactory.registerJob(
                    ActualizarDiasSinContarPuntajeHosteoHeladera.class,
                    new ActualizarDiasSinContarPuntajeHosteoHeladera(
                            new ColaboracionService(
                                    new ColaboracionRepositorio(),
                                    new DonacionDineroRepositorio(),
                                    new TransaccionService()
                            )
                    )
            );

            jobFactory.registerJob(
                    ActualizarPuntajePorHosteoHeladera.class,
                    new ActualizarPuntajePorHosteoHeladera(
                            new ColaboracionService(
                                    new ColaboracionRepositorio(),
                                    new DonacionDineroRepositorio(),
                                    new TransaccionService()
                            )
                    )
            );

            jobFactory.registerJob(
                    GenerarReportesSemanales.class,
                    new GenerarReportesSemanales(
                            new FallosHeladeraRepositorio(),
                            new ViandasHeladeraReporteRepositorio(),
                            new ViandasDonadasColaboradorRepositorio(),
                            new HeladerasRepositorio(),
                            new ColaboradorRepositorio()
                    )
            );

            jobFactory.registerJob(
                    AsignarIncidentesATecnicos.class,
                    new AsignarIncidentesATecnicos(
                            new GestorDeIncidentesService(
                                    new IncidentesRepository(),
                                    new TecnicosRepository()
                            )
                    )
            );


            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(jobFactory);

            JobDetail verificarTempHeladeras = JobBuilder.newJob(VerificarUltimaTemperaturaDeHeladeras.class)
                    .withIdentity("verificarTempHeladerasJob", "group1")
                    .build();

            JobDetail actualizarDiasHeladeras = JobBuilder.newJob(ActualizarDiasSinContarPuntajeHosteoHeladera.class)
                    .withIdentity("actualizarDiasHeladerasJob", "group1")
                    .build();

            JobDetail actualizarPuntajeHosteoHeladeras = JobBuilder.newJob(ActualizarPuntajePorHosteoHeladera.class)
                    .withIdentity("actualizarPuntajeHosteoHeladerasJob", "group1")
                    .build();
            JobDetail generarReportes = JobBuilder.newJob(GenerarReportesSemanales.class)
                    .withIdentity("generarReportesJob", "group1")
                    .build();
            JobDetail asignarIncidentesATecnicos = JobBuilder.newJob(AsignarIncidentesATecnicos.class)
                    .withIdentity("asignarIncidentesATecnicos", "group1")
                    .build();

            Trigger triggerMinutoVerificarTempHeladeras = TriggerBuilder.newTrigger()
                    .withIdentity("triggerMinutoVerificarTempHeladeras", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    .build();
            Trigger triggerMinutoAsignarIncidentesATecnicos = TriggerBuilder.newTrigger()
                    .withIdentity("triggerMinutoAsignarIncidentesATecnicos", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    .build();
            Trigger triggerDiario3amActualizarDiasHeladeras = TriggerBuilder.newTrigger()
                    .withIdentity("triggerDiario3amActualizarDiasHeladeras", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 3 * * ?"))
                    .build();
            Trigger triggerDiario3amActualizarPuntajeHosteoHeladeras = TriggerBuilder.newTrigger()
                    .withIdentity("triggerDiario3amActualizarPuntajeHosteoHeladeras", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 3 * * ?"))
                    .build();
            Trigger triggerSemanal = TriggerBuilder.newTrigger()
                    .withIdentity("triggerSemanal", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")) // Cambiar para que ejecute cada semana el lunes a las 00:00
                    .build();

            // Programar la tarea con el trigger

            // Descomentar solo lo que queremos que ejecute, con precaucion (?

            //scheduler.scheduleJob(verificarTempHeladeras, triggerMinutoVerificarTempHeladeras);
            //scheduler.scheduleJob(asignarIncidentesATecnicos, triggerMinutoAsignarIncidentesATecnicos);
            //scheduler.scheduleJob(actualizarDiasHeladeras, triggerDiario3amActualizarDiasHeladeras);
            //scheduler.scheduleJob(actualizarPuntajeHosteoHeladeras, triggerDiario3amActualizarPuntajeHosteoHeladeras);
            //scheduler.scheduleJob(generarReportes, triggerSemanal);

            // Iniciar el scheduler
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
