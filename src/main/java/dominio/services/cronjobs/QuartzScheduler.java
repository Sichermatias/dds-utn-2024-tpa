package dominio.services.cronjobs;

import dominio.services.cronjobs.tasks.VerificarUltimaTemperaturaDeHeladeras;
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
            // Crear instancia del scheduler
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            // Definir el trabajo y vincularlo a nuestra clase MyJob
            JobDetail jobDetail = JobBuilder.newJob(VerificarUltimaTemperaturaDeHeladeras.class)
                    .withIdentity("myJob", "group1")
                    .build();

            // Crear el trigger con la expresión cron para ejecutarse cada 60 segundos
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/60 * * * * ?"))
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
