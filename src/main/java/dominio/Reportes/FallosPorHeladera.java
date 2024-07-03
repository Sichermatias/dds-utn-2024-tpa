package dominio.Reportes;
import dominio.infraestructura.Heladera;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FallosPorHeladera {
    private Heladera heladera;
    private LocalDate fechaDeReporteSemanal;
    private int cantFallosHeladera;

    public FallosPorHeladera(Heladera heladera, LocalDate fechaDeReporteSemanal) {
        this.heladera = heladera;
        this.fechaDeReporteSemanal = fechaDeReporteSemanal;
    }
    public void setFechaReporte(String fecha){
        fechaDeReporteSemanal=LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void fallosSemanalesHeladera() {
        this.cantFallosHeladera = this.heladera.getCantSemanalIncidentes();
    }
}
