package dominio.services.cronjobs;

import dominio.services.cronjobs.tasks.GernerarReportesSemanales;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzSchedulerSemanal {

    public static void main(String[] args) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDetail job = JobBuilder.newJob(GernerarReportesSemanales.class)
                .withIdentity("weeklyJob", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("weeklyTrigger", "group1")
                .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(1, 0, 0)) // Ejecutar cada semana el lunes a las 00:00
                .build();

        scheduler.start();
        scheduler.scheduleJob(job, trigger);


    }
}
