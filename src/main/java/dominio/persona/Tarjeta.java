package dominio.persona;

import dominio.Persistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tarjeta")
public class Tarjeta extends Persistente {
    @Column(name = "codigo", columnDefinition = "VARCHAR(20)")
    private String codigo;
}
