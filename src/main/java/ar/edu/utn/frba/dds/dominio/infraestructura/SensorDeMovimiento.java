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

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sensorMovimiento_id", referencedColumnName = "id")
    private List<RegistroSensor> registros;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    //TODO: llevarlo a una config
    @Transient
    private static int valorPredeterminadoMovimiento;

    public static RegistroSensor crearRegistro(LocalDateTime fechaHora) {
        RegistroSensor registroSensor = new RegistroSensor();
        registroSensor.setFechaHoraRegistro(fechaHora);
        registroSensor.setValor(valorPredeterminadoMovimiento);
        return registroSensor;
    }

    public void agregarRegistro(RegistroSensor registroSensor) {
        this.registros.add(registroSensor);
    }
}