package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class SensorDeMovimiento {
    private List<RegistroSensor> registros;
    private Heladera heladera;
    private static int valorPredeterminadoRegistros;

    public void agregarRegistro(LocalDateTime fechaHora) {
        RegistroSensor registroSensor = new RegistroSensor();
        registroSensor.setFechaHoraRegistro(fechaHora);
        registroSensor.setValor(valorPredeterminadoRegistros);
        this.registros.add(registroSensor);
    }
}
