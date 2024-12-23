package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.persona.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "registrarPersonasVulnerables")
public class RegistrarPersonasVulnerables extends Persistente {
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaboracion_id")
    @Setter @Getter
    private Colaboracion colaboracion;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "personaVulnerable_id", referencedColumnName = "id")
    @Setter@Getter
    private PersonaVulnerable personaVulnerable;

    public RegistrarPersonasVulnerables() {
        this.colaboracion = new Colaboracion();
    }
    public Double puntaje(){
        return 2.0;
    }
}
