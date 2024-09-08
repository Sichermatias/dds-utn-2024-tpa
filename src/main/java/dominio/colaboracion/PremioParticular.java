package dominio.colaboracion;

import dominio.persona.Colaborador;

import javax.persistence.*;

@Entity
@Table(name = "premioParticular")
public class PremioParticular {
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
