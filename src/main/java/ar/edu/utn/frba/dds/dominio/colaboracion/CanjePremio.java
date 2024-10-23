package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;

import javax.persistence.*;

@Entity
@Table(name = "canje_premio")
public class CanjePremio extends Persistente {
    @OneToOne()
    @JoinColumn(name = "transaccion_id", referencedColumnName = "id")
    private Transaccion transaccion;

    @ManyToOne
    @JoinColumn(name = "premioCatalogo_id", referencedColumnName = "id")
    private Premio premio;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;
}
