package dominio.colaboracion;

import dominio.persona.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "registrarPersonasVulnerables")
public class RegistrarPersonasVulnerables {
    @OneToOne
    @JoinColumn(name = "colaboracion_id")
    @Setter @Getter
    private Colaboracion colaboracion;

    @OneToOne
    @JoinColumn(name = "personaVulnerable_id", referencedColumnName = "id")
    private PersonaVulnerable personaVulnerable;

    public RegistrarPersonasVulnerables() {
        this.colaboracion = new Colaboracion();
    }
}
