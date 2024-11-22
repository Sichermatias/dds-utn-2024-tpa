package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "transaccion")
public class Transaccion extends Persistente {
    @Column(name = "montoPuntaje")
    private Double montoPuntaje;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;
}
