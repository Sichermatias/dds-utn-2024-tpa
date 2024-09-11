package dominio.formulario;

import dominio.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campoRespondido")
public class CampoRespondido extends Persistente {
    @ManyToOne
    @JoinColumn(name = "campo_id")
    private Campo campo;

    @ManyToMany
    @JoinTable(
            name = "opcion_campoRespondido",
            joinColumns = @JoinColumn(name = "campoRespondido_id"),
            inverseJoinColumns = @JoinColumn(name = "opcion_id")
    )
    private List<Opcion> opcionesElegidas=new ArrayList<>();

    @Column(name = "respuestaIngresada", columnDefinition = "TEXT")
    private String respuestaIngresada;
}
