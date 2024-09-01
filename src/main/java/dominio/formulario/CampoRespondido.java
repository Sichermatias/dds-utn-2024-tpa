package dominio.formulario;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "campoRespondido")
public class CampoRespondido {
    @ManyToOne
    @JoinColumn(name = "campo_id")
    private Campo campo;

    @ManyToMany
    @JoinTable(
            name = "opcion_campoRespondido",
            joinColumns = @JoinColumn(name = "campoRespondido_id"),
            inverseJoinColumns = @JoinColumn(name = "opcion_id")
    )
    private ArrayList<Opcion> opcionesElegidas=new ArrayList<>();

    @Column(name = "respuestaIngresada", columnDefinition = "TEXT")
    private String respuestaIngresada;
}
