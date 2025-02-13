package ar.edu.utn.frba.dds.middlewares;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.extern.slf4j.Slf4j;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Counter;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MetricsMiddleware implements Handler {
    private final MeterRegistry meterRegistry;

    public MetricsMiddleware(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        if (ctx.path().startsWith("/assets") || ctx.path().startsWith("/css") || ctx.path().startsWith("/js")) {
            return;
        } else {
            long startTime = System.nanoTime();

            long duration = System.nanoTime() - startTime;
            double durationMs = duration / 1000.0;

            // Incrementar contador de visitas
            Counter.builder("http.solicitudes")
                    .tag("path", ctx.path())
                    .tag("method", String.valueOf(ctx.method()))
                    .register(meterRegistry)
                    .increment();

            // Medir tiempo de respuesta
            Timer.builder("http.respuesta.time")
                    .tag("path", ctx.path())
                    .tag("method", String.valueOf(ctx.method()))
                    .register(meterRegistry)
                    .record(duration, TimeUnit.MILLISECONDS);

            log.info("Request [{}] {} -> {} ({} ms)", ctx.method(), ctx.path(), ctx.status(), durationMs);
        }

    }
}