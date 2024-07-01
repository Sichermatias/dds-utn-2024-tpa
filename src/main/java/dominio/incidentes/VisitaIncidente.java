package dominio.incidentes;
import dominio.persona.Tecnico;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
public class VisitaIncidente {
    private Tecnico tecnicoAsignado;
    private Incidente incidenteAResolver;
    private LocalDateTime fechaVisita;
    private String fotoVisita;
    private Boolean incidenteResuelto;

    public VisitaIncidente(Tecnico tecnicoAsignado, Incidente incidenteAResolver, LocalDateTime fechaVisita, String fotoVisita, Boolean incidenteResuelto) {
        this.tecnicoAsignado = tecnicoAsignado;
        this.incidenteAResolver = incidenteAResolver;
        this.fechaVisita = fechaVisita;
        this.fotoVisita = fotoVisita;
        this.incidenteResuelto = incidenteResuelto;
    }
}
