package ar.edu.utn.frba.dds.dominio.persona;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tarjeta")
public class Tarjeta extends Persistente {
    @Column(name = "codigo", columnDefinition = "VARCHAR(20)")
    @Getter@Setter
    private String codigo;
}
