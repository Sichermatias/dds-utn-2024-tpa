package dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.models.repositories.imp.FallosPorHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.imp.ReporteViandasHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.imp.ReportesSemanalesRepository;
import ar.edu.utn.frba.dds.models.repositories.imp.ViandasDonadasPorColaboradorRepository;
import dominio.reportes.FallosPorHeladera;
import dominio.reportes.ReporteViandasHeladera;
import dominio.reportes.ViandasDonadasPorColaborador;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;

public class GenerarReportesSemanales implements Job{
    private FallosPorHeladeraRepository repositorioFallosHeladera;
    private ReporteViandasHeladeraRepository repositorioViandasHeladera;
    private ViandasDonadasPorColaboradorRepository repositorioViandasColaborador;
    private ReportesSemanalesRepository reportesRepository;

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Generando Los reportes Semanales");

        List<FallosPorHeladera> fallosList = repositorioFallosHeladera.obtenerReportes();
        List<ReporteViandasHeladera> reporteList = repositorioViandasHeladera.obtenerReportes();
        List<ViandasDonadasPorColaborador> viandasList = repositorioViandasColaborador.obtenerReportes();

        this.reportesRepository = new ReportesSemanalesRepository(fallosList, reporteList, viandasList);

    }
}



