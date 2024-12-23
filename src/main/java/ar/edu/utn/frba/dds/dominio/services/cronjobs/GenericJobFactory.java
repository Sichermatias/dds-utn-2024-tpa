package ar.edu.utn.frba.dds.dominio.services.cronjobs;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import java.util.HashMap;
import java.util.Map;

public class GenericJobFactory implements JobFactory {
    private final Map<Class<? extends Job>, Job> jobInstances = new HashMap<>();

    // Método para registrar Jobs con sus dependencias
    public <T extends Job> void registerJob(Class<T> jobClass, T jobInstance) {
        jobInstances.put(jobClass, jobInstance);
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        Class<? extends Job> jobClass = bundle.getJobDetail().getJobClass();
        Job job = jobInstances.get(jobClass);

        if (job == null) {
            throw new SchedulerException("No se encontró una instancia registrada para el Job: " + jobClass.getName());
        }
        return job;
    }
}
