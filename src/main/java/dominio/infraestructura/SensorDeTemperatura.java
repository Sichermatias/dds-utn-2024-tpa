package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SensorDeTemperatura {
    private List<RegistroSensor> registros;
    private Heladera heladera;
    private EvaluadorTemperatura evaluadorTemperatura;

    public SensorDeTemperatura() {
        this.registros = new ArrayList<>();
    }

    public RegistroSensor ultimoRegistro() {
        //TODO: encontrar ultimo registro en lista
        return null;
    }

    public void agregarRegistro(Double temperatura, LocalDateTime fechaHoraRegistro) {
        RegistroSensor registroSensor = new RegistroSensor();
        registroSensor.setFechaHoraRegistro(fechaHoraRegistro);
        registroSensor.setValor(temperatura.intValue());

        this.registros.add(registroSensor);
    }
}
