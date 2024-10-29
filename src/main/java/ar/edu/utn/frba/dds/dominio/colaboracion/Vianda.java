package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "vianda")
@Setter@Getter
public class Vianda extends Persistente {
    public Vianda(){}
    public Vianda(String nombreComida, LocalDate fechaCaducidad, LocalDate fechaDonacion, Heladera heladeraAsignada, Double calorias, Double peso, Boolean fueEntregado) {
        this.nombreComida = nombreComida;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaDonacion = fechaDonacion;
        this.heladeraAsignada = heladeraAsignada;
        this.calorias = calorias;
        this.peso = peso;
        this.fueEntregado = fueEntregado;
    }
    @Column(name = "nombreComida", columnDefinition = "VARCHAR(100)")
    private String nombreComida;

    @Column(name = "fechaCaducidad", columnDefinition = "DATE")
    private LocalDate fechaCaducidad;

    @Column(name = "fechaDonacion", columnDefinition = "DATE")
    private LocalDate fechaDonacion;

    @ManyToOne
    @JoinColumn(name = "heladeraAsignada_id", referencedColumnName = "id")
    private Heladera heladeraAsignada;

    @Column(name = "calorias", columnDefinition = "DOUBLE")
    private Double calorias;

    @Column(name = "peso", columnDefinition = "DOUBLE")
    private Double peso;

    @Column(name = "fueEntregado", columnDefinition = "BIT(1)")
    private Boolean fueEntregado;

    @Column(name = "codigo", columnDefinition = "VARCHAR(50)")
    private String codigo;
}
