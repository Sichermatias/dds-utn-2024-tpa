package ar.edu.utn.frba.dds.dominio.persona;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tarjeta")
public class Tarjeta extends Persistente {
    @Column(name = "codigo", columnDefinition = "VARCHAR(20)")
    private String codigo;

    @Column(name = "colaborador_id", columnDefinition = "INTEGER(5)")
    private Long colabodador_id;

    @Column(name = "persona_vulnerable_id", columnDefinition = "INTEGER(5)")
    private Long personaVulnerable_id;
}
