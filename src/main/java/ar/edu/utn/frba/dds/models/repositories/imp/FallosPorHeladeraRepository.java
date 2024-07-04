package ar.edu.utn.frba.dds.models.repositories.imp;
import dominio.reportes.FallosPorHeladera;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class FallosPorHeladeraRepository {
    private List<FallosPorHeladera> reportesFallosHeladera;

    public FallosPorHeladeraRepository() {
        this.reportesFallosHeladera = new ArrayList<>();
    }
    public void agregarReporte(FallosPorHeladera reporte) {
        this.reportesFallosHeladera.add(reporte);
    }

    public List<FallosPorHeladera> obtenerReportes() {
        return this.reportesFallosHeladera;
    }

    public FallosPorHeladera buscarReportePorFecha(LocalDate fecha) {
        for (FallosPorHeladera reporte : reportesFallosHeladera) {
            if (reporte.getFechaDeReporteSemanal().equals(fecha)) {
                return reporte;
            }
        }
        return null;
    }
}
