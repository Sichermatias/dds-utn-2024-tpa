package ar.edu.utn.frba.dds.dominio.formulario;

import ar.edu.utn.frba.dds.dominio.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campo")
public class Campo extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoCampo tipo;

    @OneToMany
    @JoinColumn(name = "campo_id")
    private List<Opcion> opciones= new ArrayList<>();

    @Column(name = "esObligatorio", columnDefinition = "BIT(1)")
    private Boolean esObligatorio;

    @Column(name = "activo", columnDefinition = "BIT(1)")
    private Boolean activo;
}
