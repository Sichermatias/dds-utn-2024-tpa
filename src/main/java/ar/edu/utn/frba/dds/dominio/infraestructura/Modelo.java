package ar.edu.utn.frba.dds.dominio.infraestructura;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "modelo")
@Getter @Setter
public class Modelo extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
    private String nombre;

    @Column(name = "tempMinAceptable", columnDefinition = "DOUBLE")
    private Double tempMinAceptable;

    @Column(name = "tempMaxAceptable", columnDefinition = "DOUBLE")
    private Double tempMaxAceptable;
}
