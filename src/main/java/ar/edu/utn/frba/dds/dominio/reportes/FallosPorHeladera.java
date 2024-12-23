package ar.edu.utn.frba.dds.dominio.reportes;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

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

    @Column(name = "cantFallosHeladera", columnDefinition = "INTEGER")
    private int cantFallosHeladera;

    @Column(name = "fechaReporteSemanal", columnDefinition = "DATE")
    private LocalDate fechaDeReporteSemanal;

    @Column(name = "anio", columnDefinition = "INTEGER(4)")
    private int anio;

    @Column(name = "mes", columnDefinition = "INTEGER(2)")
    private int mes;

    @Column(name = "semanaDelMes", columnDefinition = "INTEGER(1)")
    private int semanaDelMes;

    public FallosPorHeladera() {
    }

    public FallosPorHeladera(LocalDate fecha) {
        this.fechaDeReporteSemanal = fecha;
        this.anio = fecha.getYear();
        this.mes = fecha.getMonthValue();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        this.semanaDelMes = fecha.get(weekFields.weekOfMonth());
    }
    public String getFechaDeReporteSemanalFormatted() {
        return fechaDeReporteSemanal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void fallosSemanalesHeladera() {
        this.cantFallosHeladera = this.heladera.getCantSemanalIncidentes();
    }
}
