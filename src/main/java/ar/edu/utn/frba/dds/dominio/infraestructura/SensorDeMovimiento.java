package ar.edu.utn.frba.dds.dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensorDeMovimiento")
@Getter @Setter
public class SensorDeMovimiento {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "codigo", columnDefinition = "VARCHAR(50)")
    private String codigo;

    @OneToMany
    @JoinColumn(name = "sensorMovimiento_id", referencedColumnName = "id")
    private List<RegistroSensor> registros;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    //TODO: llevarlo a una config
    @Transient
    private static int valorPredeterminadoMovimiento;

    public void agregarRegistro(LocalDateTime fechaHora) {
        RegistroSensor registroSensor = new RegistroSensor();
        registroSensor.setFechaHoraRegistro(fechaHora);
        registroSensor.setValor(valorPredeterminadoMovimiento);
        this.registros.add(registroSensor);
    }
}