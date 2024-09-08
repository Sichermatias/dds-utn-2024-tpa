package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import dominio.persona.Tarjeta;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "pedidoDeApertura")
public class PedidoDeApertura {
    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @ManyToOne
    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    private Tarjeta tarjeta;

    @Column(name = "fechaHoraRealizada")
    private LocalDateTime fechaHoraRealizada;

    @Column(name = "valido", columnDefinition = "BIT(1)")
    private Boolean valido;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    public PedidoDeApertura() {
    }
}
