package dominio.reportes;

import dominio.persona.Colaborador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "viandasDonadasPorColaborador")
@Getter @Setter
public class ViandasDonadasPorColaborador {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @Column(name = "fechaReporteSemanal", columnDefinition = "DATE")
    private LocalDate fechaDeReporteSemanal;

    @Column(name = "cantViandasDonadas", columnDefinition = "INTEGER")
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
