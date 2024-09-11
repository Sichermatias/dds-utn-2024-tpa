package dominio.colaboracion;

import dominio.Persistente;
import dominio.infraestructura.Heladera;
import dominio.persona.Tarjeta;
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