package ar.edu.utn.frba.dds.dominio.incidentes;
import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.persona.Tecnico;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "visitaPorIncidente")
@Setter @Getter
public class VisitaIncidente extends Persistente {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnicoAsignado;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "incidente_id")
    private Incidente incidenteAResolver;

    @Column(name = "fechaVisita", columnDefinition = "DATETIME")
    private LocalDateTime fechaVisita;

    @ElementCollection //se mapea como una relación oneToMany, pero con elementos primitivos.
    @CollectionTable(name = "visita_foto_id", joinColumns = @JoinColumn(name = "visita_Incidente_id"))
    @Column(name = "foto")
    private List<String> fotosVisita;

    @Column(name = "estado_incidente", columnDefinition = "BINARY")
    private Boolean incidenteResuelto;

    public VisitaIncidente(Tecnico tecnicoAsignado, Incidente incidenteAResolver, LocalDateTime fechaVisita, Boolean incidenteResuelto) {
        this.tecnicoAsignado = tecnicoAsignado;
        this.incidenteAResolver = incidenteAResolver;
        this.fechaVisita = fechaVisita;
        this.fotosVisita = new ArrayList<>();
        this.incidenteResuelto = incidenteResuelto;
    }

    public void agregarFotos(ArrayList<String> fotos) {
        this.fotosVisita.addAll(fotos);
    }

    public void agregarFoto(String foto){
        fotosVisita.add(foto);
    }
}
