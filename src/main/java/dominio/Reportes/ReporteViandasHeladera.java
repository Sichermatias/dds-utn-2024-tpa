package dominio.Reportes;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class ReporteViandasHeladera {
    private Heladera heladera;
    private LocalDate fechaDeReporteSemanal;
    private int cantViandasColocadas;
    private int cantViandasRetiradas;

    public void setFechaReporte(String fecha){
        fechaDeReporteSemanal=LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public ReporteViandasHeladera(Heladera heladera, LocalDate fechaDeReporteSemanal) {
        this.heladera = heladera;
        this.fechaDeReporteSemanal = fechaDeReporteSemanal;
    }

    public void viandasColacadasSemanal() {
        this.cantViandasColocadas = this.heladera.getCantSemanalViandasColocadas();
    }
    public void viandasRetiradasSemanal() {
        this.cantViandasRetiradas = this.heladera.getCantSemanalViandasRetiradas();
    }
}
