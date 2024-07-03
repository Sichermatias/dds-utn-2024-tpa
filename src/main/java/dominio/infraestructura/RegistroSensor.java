package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
public class RegistroSensor {
    private LocalDateTime fechaHoraRegistro;
    private Integer valor;

    public boolean seCreoHaceMasDe(int minutos) {
        return this.fechaHoraRegistro.isBefore(LocalDateTime.now().minusMinutes(minutos));
    }
}
