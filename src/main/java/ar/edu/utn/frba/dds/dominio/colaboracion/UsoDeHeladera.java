package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Tarjeta;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "usoDeHeladera")
@Getter
@Setter
public class UsoDeHeladera extends Persistente {
    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @OneToOne
    @JoinColumn(name = "vianda_id", referencedColumnName = "id")
    private Vianda vianda;

    @ManyToOne
    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    private Tarjeta tarjeta;

    public void removerViandasDeHeladera(Integer cant){
        heladera.setCantViandasActuales(heladera.getCantViandasActuales()-cant);
    }
}