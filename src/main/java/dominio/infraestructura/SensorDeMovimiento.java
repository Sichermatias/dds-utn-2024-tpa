package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensorMovimiento")
@Getter @Setter
public class SensorDeMovimiento {
    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private List<RegistroSensor> registros;

    @OneToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;

    @Column(name = "predeterminadoRegistros", columnDefinition = "INTEGER")
    private static int valorPredeterminadoRegistros;

    public void agregarRegistro(LocalDateTime fechaHora) {
        RegistroSensor registroSensor = new RegistroSensor();
        registroSensor.setFechaHoraRegistro(fechaHora);
        registroSensor.setValor(valorPredeterminadoRegistros);
        this.registros.add(registroSensor);
    }
}
