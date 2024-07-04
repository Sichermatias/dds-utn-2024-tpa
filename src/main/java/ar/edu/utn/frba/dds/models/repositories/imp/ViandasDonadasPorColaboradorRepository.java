package ar.edu.utn.frba.dds.models.repositories.imp;
import dominio.reportes.ViandasDonadasPorColaborador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class ViandasDonadasPorColaboradorRepository {
    private List<ViandasDonadasPorColaborador> reportesViandas;

    public ViandasDonadasPorColaboradorRepository() {
        this.reportesViandas = new ArrayList<>();
    }

    public void agregarReporte(ViandasDonadasPorColaborador reporte) {
        this.reportesViandas.add(reporte);
    }

    public List<ViandasDonadasPorColaborador> obtenerReportes() {
        return this.reportesViandas;
    }

    public ViandasDonadasPorColaborador buscarReportePorFecha(LocalDate fecha) {
        for (ViandasDonadasPorColaborador reporte : reportesViandas) {
            if (reporte.getFechaDeReporteSemanal().equals(fecha)) {
                return reporte;
            }
        }
        return null;
    }
}
