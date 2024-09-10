package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hostearHeladera")
@Getter @Setter
public class HostearHeladera{
    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @OneToOne
    @JoinColumn(name = "colaboracion_id")
    private Colaboracion colaboracion;

    public HostearHeladera() {
        this.colaboracion = new Colaboracion();
    }
}
