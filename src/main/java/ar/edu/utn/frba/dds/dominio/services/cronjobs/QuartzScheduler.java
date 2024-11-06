package ar.edu.utn.frba.dds.dominio.services.cronjobs;

import ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks.ActualizarDiasSinContarPuntajeHosteoHeladera;
import ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks.ActualizarPuntajePorHosteoHeladera;
import ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks.VerificarUltimaTemperaturaDeHeladeras;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzScheduler {
    public void start() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            JobDetail verificarTempHeladeras = JobBuilder.newJob(VerificarUltimaTemperaturaDeHeladeras.class)
                    .withIdentity("verificarTempHeladerasJob", "group1")
                    .build();

            JobDetail actualizarDiasHeladeras = JobBuilder.newJob(ActualizarDiasSinContarPuntajeHosteoHeladera.class)
                    .withIdentity("actualizarDiasHeladerasJob", "group1")
                    .build();

            JobDetail actualizarPuntajeHosteoHeladeras = JobBuilder.newJob(ActualizarPuntajePorHosteoHeladera.class)
                    .withIdentity("actualizarPuntajeHosteoHeladerasJob", "group1")
                    .build();

            Trigger triggerMinuto = TriggerBuilder.newTrigger()
                    .withIdentity("triggerMinuto", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("* 0/1 * * * ?"))
                    .build();
            Trigger triggerDiario3am = TriggerBuilder.newTrigger()
                    .withIdentity("triggerDiario3am", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 3 * * ?"))
                    .build();

            // Programar la tarea con el trigger
            scheduler.scheduleJob(verificarTempHeladeras, triggerMinuto);
            scheduler.scheduleJob(actualizarDiasHeladeras, triggerDiario3am);
            scheduler.scheduleJob(actualizarPuntajeHosteoHeladeras, triggerDiario3am);

            // Iniciar el scheduler
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
