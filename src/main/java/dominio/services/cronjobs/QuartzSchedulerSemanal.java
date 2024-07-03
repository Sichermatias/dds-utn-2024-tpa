package dominio.services.cronjobs;

import dominio.services.cronjobs.tasks.GernerarReportesSemanales;
import dominio.services.cronjobs.tasks.VerificarUltimaTemperaturaDeHeladeras;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzSchedulerSemanal {

    public void start(){

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail job = JobBuilder.newJob(GernerarReportesSemanales.class)
                    .withIdentity("weeklyJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("weeklyTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("* 0/1 * * * ?")) // Ejecutar cada semana el lunes a las 00:00
                    .build();

            scheduler.start();
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
