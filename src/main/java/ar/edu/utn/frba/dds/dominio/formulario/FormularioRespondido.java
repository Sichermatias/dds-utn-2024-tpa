package ar.edu.utn.frba.dds.dominio.formulario;

import ar.edu.utn.frba.dds.dominio.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "formularioRespondido")
public class FormularioRespondido extends Persistente {
    @ManyToOne
    @JoinColumn(name = "formulario_id")
    private Formulario formulario;

    @OneToMany
    @JoinColumn(name = "formularioRespondido_id")
    private List<CampoRespondido> camposRespondidos= new ArrayList<>();
}
