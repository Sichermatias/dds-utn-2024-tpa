package ar.edu.utn.frba.dds.dominio.infraestructura;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registroSensor")
@Setter @Getter
public class RegistroSensor extends Persistente {
    @Column(name = "fechaHoraRegistro", columnDefinition = "DATETIME")
    private LocalDateTime fechaHoraRegistro;

    @Column(name = "valor", columnDefinition = "INTEGER(7)")
    private Integer valor;

    public boolean seCreoHaceMasDe(int minutos) {
        return this.fechaHoraRegistro.isBefore(LocalDateTime.now().minusMinutes(minutos));
    }
}