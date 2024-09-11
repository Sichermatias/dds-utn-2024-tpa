package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;

@Entity
@Table(name = "RegistroDatoSensor")
@Setter @Getter
public class RegistroSensor {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "FechaHora", columnDefinition = "DATETIME")//converter
    private LocalDateTime fechaHoraRegistro;

    @Column(name = "datoValor", columnDefinition = "INTEGER")
    private Integer valor;

    public boolean seCreoHaceMasDe(int minutos) {
        return this.fechaHoraRegistro.isBefore(LocalDateTime.now().minusMinutes(minutos));
    }
}
