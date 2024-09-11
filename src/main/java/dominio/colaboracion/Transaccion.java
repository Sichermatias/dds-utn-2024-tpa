package dominio.colaboracion;

import dominio.Persistente;
import dominio.persona.Colaborador;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
public class Transaccion extends Persistente {
    @Column(name = "montoPuntaje")
    private Double montoPuntaje;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;

    //se va por persistente?
    private LocalDateTime fechaHora;
}
