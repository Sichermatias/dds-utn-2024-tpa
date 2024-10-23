package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rubroPremio")
@Getter
@Setter
public class RubroPremio extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
}
