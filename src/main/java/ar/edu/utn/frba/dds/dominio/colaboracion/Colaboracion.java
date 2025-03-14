package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "colaboracion")

public class Colaboracion extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "tipo", columnDefinition = "VARCHAR(50)")
    private String tipo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fechaColaboracion", columnDefinition = "DATE")
    public LocalDate fechaColaboracion;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "transaccion_id")
    private Transaccion transaccion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;
}
