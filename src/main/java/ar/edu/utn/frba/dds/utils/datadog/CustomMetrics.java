package ar.edu.utn.frba.dds.utils.datadog;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

public class CustomMetrics {

    private final MeterRegistry meterRegistry;

    public CustomMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // Incrementar el contador para el número de consultas
    public void incrementQueryCount() {
        Counter queryCounter = meterRegistry.counter("custom.query.count");
        queryCounter.increment();  // Incrementar el contador
    }

    // Registrar la duración de una consulta en milisegundos
    public void recordQueryDuration(long durationInMillis) {
        Timer queryTimer = meterRegistry.timer("custom.query.duration");
        queryTimer.record(durationInMillis, java.util.concurrent.TimeUnit.MILLISECONDS);  // Registrar la duración
    }
}