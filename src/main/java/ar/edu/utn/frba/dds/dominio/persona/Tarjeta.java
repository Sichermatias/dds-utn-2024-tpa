package ar.edu.utn.frba.dds.dominio.persona;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tarjeta")
public class Tarjeta extends Persistente {
    @Column(name = "codigo", columnDefinition = "VARCHAR(20)")
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "persona_vulnerable_id", referencedColumnName = "id")
    private PersonaVulnerable personaVulnerable;
}
