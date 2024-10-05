package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;

import javax.persistence.*;

@Entity
@Table(name = "premioParticular")
public class PremioParticular extends Persistente {
    @OneToOne()
    @JoinColumn(name = "transaccion_id", referencedColumnName = "id")
    private Transaccion transaccion;

    @ManyToOne
    @JoinColumn(name = "premioCatalogo_id", referencedColumnName = "id")
    private PremioCatalogo premioCatalogo;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;
}
