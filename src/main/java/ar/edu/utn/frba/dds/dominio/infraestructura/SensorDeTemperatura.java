package ar.edu.utn.frba.dds.dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sensorDeTemperatura")
@Getter @Setter
public class SensorDeTemperatura {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "codigo", columnDefinition = "VARCHAR(50)")
    private String codigo;

    @OneToMany
    @JoinColumn(name = "sensorTemperatura_id", referencedColumnName = "id")
    private List<RegistroSensor> registros;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @Transient
    private EvaluadorTemperatura evaluadorTemperatura;

    public SensorDeTemperatura() {
        this.registros = new ArrayList<>();
        this.evaluadorTemperatura = new EvaluadorTemperatura();
    }

    public RegistroSensor ultimoRegistro() {
        if (!this.registros.isEmpty()) {
            return this.registros.get(this.registros.size() - 1);
        }
        return null;
    }

    public static RegistroSensor crearRegistro(Double temperatura, LocalDateTime fechaHoraRegistro) {
        RegistroSensor registroSensor = new RegistroSensor();
        registroSensor.setFechaHoraRegistro(fechaHoraRegistro);
        registroSensor.setValor(temperatura.intValue());
        return registroSensor;
    }

    public void agregarRegistro(RegistroSensor registroSensor) {
        this.registros.add(registroSensor);
    }

    public boolean ultimoRegistroSeCreoHaceMasDe(int minutos) {
        return this.ultimoRegistro().seCreoHaceMasDe(minutos);
    }
}