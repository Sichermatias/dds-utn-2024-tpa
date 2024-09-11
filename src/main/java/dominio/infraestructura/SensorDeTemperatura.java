package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sensorTemperatura")
@Getter @Setter
public class SensorDeTemperatura {
    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private List<RegistroSensor> registros;

    @OneToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;

    @Transient
    private EvaluadorTemperatura evaluadorTemperatura;

    public SensorDeTemperatura() {
        this.registros = new ArrayList<>();
    }

    public RegistroSensor ultimoRegistro() {
        if (!this.registros.isEmpty()) {
            return this.registros.get(this.registros.size() - 1);
        }
        return null;
    }

    public void agregarRegistro(Double temperatura, LocalDateTime fechaHoraRegistro) {
        RegistroSensor registroSensor = new RegistroSensor();
        registroSensor.setFechaHoraRegistro(fechaHoraRegistro);
        registroSensor.setValor(temperatura.intValue());

        this.registros.add(registroSensor);
    }

    public boolean ultimoRegistroSeCreoHaceMasDe(int minutos) {
        return this.ultimoRegistro().seCreoHaceMasDe(minutos);
    }
}
