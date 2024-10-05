package ar.edu.utn.frba.dds.dominio.services.cronjobs;

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

            JobDetail jobDetail = JobBuilder.newJob(VerificarUltimaTemperaturaDeHeladeras.class)
                    .withIdentity("myJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("* 0/1 * * * ?"))
                    .build();

            // Programar la tarea con el trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // Iniciar el scheduler
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
