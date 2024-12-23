package ar.edu.utn.frba.dds.dominio.formulario;

import ar.edu.utn.frba.dds.dominio.Persistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "opcion")
public class Opcion extends Persistente {
    @Column(name = "textoOpcion", columnDefinition = "TEXT")
    private String textoOpcion;

    @Column(name = "codigo", columnDefinition = "TINYINT")
    private Integer nroOpcion;
}
