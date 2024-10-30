package ar.edu.utn.frba.dds.dominio.reportes;

import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
@Table(name = "reporteViandasXHeladera")
@Getter @Setter
public class ReporteViandasHeladera {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;

    @Column(name = "fechaReporteSemanal", columnDefinition = "DATE")
    private LocalDate fechaDeReporteSemanal;

    @Column(name = "cantViandasColocadas", columnDefinition = "INTEGER")
    private int cantViandasColocadas;

    @Column(name = "cantViandasRetiradas", columnDefinition = "INTEGER")
    private int cantViandasRetiradas;

    public void setFechaReporte(String fecha){
        fechaDeReporteSemanal=LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public ReporteViandasHeladera(Heladera heladera, LocalDate fechaDeReporteSemanal) {
        this.heladera = heladera;
        this.fechaDeReporteSemanal = fechaDeReporteSemanal;
    }

    public ReporteViandasHeladera() {
       }


    public void viandasColacadasSemanal() {
        this.cantViandasColocadas = this.heladera.getCantSemanalViandasColocadas();
    }
    public void viandasRetiradasSemanal() {
        this.cantViandasRetiradas = this.heladera.getCantSemanalViandasRetiradas();
    }
}
