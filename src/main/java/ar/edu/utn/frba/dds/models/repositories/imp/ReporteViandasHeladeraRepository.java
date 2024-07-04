package ar.edu.utn.frba.dds.models.repositories.imp;
import dominio.reportes.ReporteViandasHeladera;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class ReporteViandasHeladeraRepository {
    private List<ReporteViandasHeladera> reportesViandas;

    public ReporteViandasHeladeraRepository() {
        this.reportesViandas = new ArrayList<>();
    }

    public void agregarReporte(ReporteViandasHeladera reporte) {
        this.reportesViandas.add(reporte);
    }

    public List<ReporteViandasHeladera> obtenerReportes() {
        return this.reportesViandas;
    }

    public ReporteViandasHeladera buscarReportePorFecha(LocalDate fecha) {
        for (ReporteViandasHeladera reporte : reportesViandas) {
            if (reporte.getFechaDeReporteSemanal().equals(fecha)) {
                return reporte;
            }
        }
        return null;
    }
}
