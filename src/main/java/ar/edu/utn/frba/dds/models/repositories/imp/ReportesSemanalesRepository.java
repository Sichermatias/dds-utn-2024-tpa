package ar.edu.utn.frba.dds.models.repositories.imp;
import ar.edu.utn.frba.dds.dominio.reportes.FallosPorHeladera;
import ar.edu.utn.frba.dds.dominio.reportes.ReporteViandasHeladera;
import ar.edu.utn.frba.dds.dominio.reportes.ViandasDonadasPorColaborador;

import java.util.List;
public class ReportesSemanalesRepository {
    private List<FallosPorHeladera> fallosList;
    private List<ReporteViandasHeladera> reporteList;
    private List<ViandasDonadasPorColaborador> viandasList;

    public List<FallosPorHeladera> obtenerReporteSemanaPreviaPorFallosHeladera() {
        return this.fallosList;
    }
    public List<ReporteViandasHeladera> obtenerReporteSemanaPreviaPorViandasHeladera() {
        return this.reporteList;
    }
    public List<ViandasDonadasPorColaborador> obtenerReporteSemanaPreviaPorViandasColaborador() {
        return this.viandasList;
    }

    public ReportesSemanalesRepository(List<FallosPorHeladera> fallosList, List<ReporteViandasHeladera> reporteList, List<ViandasDonadasPorColaborador> viandasList) {
        this.fallosList = fallosList;
        this.reporteList = reporteList;
        this.viandasList = viandasList;
    }
}
