package dominio.formulario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "formularioRespondido")
public class FormularioRespondido {
    @ManyToOne
    @JoinColumn(name = "formulario_id")
    private Formulario formulario;

    @OneToMany
    @JoinColumn(name = "formularioRespondido_id")
    private List<CampoRespondido> camposRespondidos= new ArrayList<>();
}
