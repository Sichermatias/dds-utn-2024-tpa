package dominio.Reportes;

import dominio.persona.Colaborador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class ViandasDonadasPorColaborador {
    private Colaborador colaborador;
    private LocalDate fechaDeReporteSemanal;
    private int cantViandasDonadas;

    public ViandasDonadasPorColaborador(Colaborador colaborador, LocalDate fechaDeReporteSemanal) {
        this.colaborador = colaborador;
        this.fechaDeReporteSemanal = fechaDeReporteSemanal;
    }
    public void setFechaReporte(String fecha){
        fechaDeReporteSemanal=LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public void viandasDonadasSemanal() {
        this.cantViandasDonadas = this.colaborador.getCantSemanalViandasDonadas();
    }
}
