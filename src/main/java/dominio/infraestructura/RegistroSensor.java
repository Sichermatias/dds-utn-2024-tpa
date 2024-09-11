package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registroSensor")
@Setter @Getter
public class RegistroSensor {
    @Column(name = "fechaHoraRegistro", columnDefinition = "DATETIME")
    private LocalDateTime fechaHoraRegistro;

    @Column(name = "valor", columnDefinition = "TINYINT")
    private Integer valor;

    public boolean seCreoHaceMasDe(int minutos) {
        return this.fechaHoraRegistro.isBefore(LocalDateTime.now().minusMinutes(minutos));
    }
}