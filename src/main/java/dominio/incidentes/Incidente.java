package dominio.incidentes;

import dominio.infraestructura.Heladera;
import dominio.persona.Colaborador;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
@Setter @Getter
public class Incidente {
    private LocalDateTime fechaIncidente;
    private Heladera heladeraIncidente;
    private TipoIncidente tipoIncidente;
    private Colaborador colaboradorIncidente;
    private String descripcionIncidente;
    private String fotoIncidente;

    public Incidente(LocalDateTime fechaIncidente, Heladera heladeraIncidente, TipoIncidente tipoIncidente, Colaborador colaboradorIncidente, String descripcionIncidente, String fotoIncidente) {
        this.fechaIncidente = fechaIncidente;
        this.heladeraIncidente = heladeraIncidente;
        this.tipoIncidente = tipoIncidente;
        this.colaboradorIncidente = colaboradorIncidente;
        this.descripcionIncidente = descripcionIncidente;
        this.fotoIncidente = fotoIncidente;
    }

    public Incidente() {

    }
}
