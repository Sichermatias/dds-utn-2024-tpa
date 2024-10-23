package ar.edu.utn.frba.dds.dominio.reportes;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fallosHeladera")
@Getter @Setter
public class FallosPorHeladera {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;

    @Column(name = "fechaReporteSemanal", columnDefinition = "DATE")
    private LocalDate fechaDeReporteSemanal;

    @Column(name = "cantFallosHeladera", columnDefinition = "INTEGER")
    private int cantFallosHeladera;

    public FallosPorHeladera() {
    }
    public void setFechaReporte(String fecha){
        fechaDeReporteSemanal=LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public String getFechaDeReporteSemanalFormatted() {
        return fechaDeReporteSemanal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void fallosSemanalesHeladera() {
        this.cantFallosHeladera = this.heladera.getCantSemanalIncidentes();
    }
}
