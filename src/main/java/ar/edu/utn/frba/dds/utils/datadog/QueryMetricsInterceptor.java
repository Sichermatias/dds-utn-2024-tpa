package ar.edu.utn.frba.dds.utils.datadog;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.datadog.DatadogConfig;
import io.micrometer.datadog.DatadogMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class QueryMetricsInterceptor implements StatementInspector {

    // Constructor vacío requerido por Hibernate
    public QueryMetricsInterceptor() {
        // Este constructor vacío es necesario para que Hibernate lo instancie
    }

    private final MeterRegistry meterRegistry = DatadogMeterRegistry.builder(new DatadogConfig() {
        @Override
        public Duration step() {
            return Duration.ofSeconds(10);
        }

        @Override
        public String apiKey() {
            return "598a822d3c562bcd031a855cb57a1b8e";
        }

        @Override
        public String applicationKey() {
            return "ff1d71828a4129f3a486fd729acc4a6823ca8acc";
        }

        @Override
        public String uri() {
            return "https://api.us5.datadoghq.com";
        }

        @Override
        public String get(String k) {
            return null; // accept the rest of the defaults
        }
    }).build();

    // Constructor para recibir la configuración de Micrometer (no es utilizado por Hibernate)

    @Override
    public String inspect(String sql) {
        long startTime = System.nanoTime();

        // Ejecutar SQL (esto es solo un ejemplo, no ejecuta el SQL en realidad)
        // Aquí normalmente ejecutarías el SQL con Hibernate

        long duration = System.nanoTime() - startTime;

        // Incrementar el contador de consultas
        Counter queryCounter = meterRegistry.counter("custom.query.count");
        queryCounter.increment();

        // Registrar la duración de la consulta
        Timer queryTimer = meterRegistry.timer("custom.query.duration");
        queryTimer.record(duration , TimeUnit.MILLISECONDS); // En milisegundos


        log.info("Ejecutando SQL: " + sql);
        log.info("Duración de la consulta: " + duration + " ms");

        return sql;
    }
}