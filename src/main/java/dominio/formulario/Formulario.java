package dominio.formulario;

import dominio.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "formulario")
public class Formulario extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @OneToMany
    @JoinColumn(name = "formulario_id")
    private List<Campo> campos= new ArrayList<>();
}
