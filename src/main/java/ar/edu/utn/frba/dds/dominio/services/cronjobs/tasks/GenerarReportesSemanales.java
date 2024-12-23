package ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks;

import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.dominio.reportes.FallosPorHeladera;
import ar.edu.utn.frba.dds.dominio.reportes.ReporteViandasHeladera;
import ar.edu.utn.frba.dds.dominio.reportes.ViandasDonadasPorColaborador;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenerarReportesSemanales implements Job{
    private final FallosHeladeraRepositorio repositorioFallosHeladera;
    private final ViandasHeladeraReporteRepositorio repositorioViandasHeladera;
    private final ViandasDonadasColaboradorRepositorio repositorioViandasColaborador;
    private final HeladerasRepositorio heladerasRepositorio;

    private final ColaboradorRepositorio colaboradorRepositorio;

    public GenerarReportesSemanales(FallosHeladeraRepositorio repositorioFallosHeladera, ViandasHeladeraReporteRepositorio repositorioViandasHeladera, ViandasDonadasColaboradorRepositorio repositorioViandasColaborador, HeladerasRepositorio heladerasRepositorio, ColaboradorRepositorio colaboradorRepositorio) {
        this.repositorioFallosHeladera = repositorioFallosHeladera;
        this.repositorioViandasHeladera = repositorioViandasHeladera;
        this.repositorioViandasColaborador = repositorioViandasColaborador;
        this.heladerasRepositorio = heladerasRepositorio;
        this.colaboradorRepositorio = colaboradorRepositorio;
    }

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Generando Los reportes Semanales");

        LocalDate fechaActual = LocalDate.now();

        List<FallosPorHeladera> fallosList = this.obtenerFallosPorHeladera(fechaActual);
        for (FallosPorHeladera fallosPorHeladera: fallosList)
            this.repositorioFallosHeladera.persistir(fallosPorHeladera);
        List<ReporteViandasHeladera> reporteList = this.obtenerViandasPorHeladera(fechaActual);
        for (ReporteViandasHeladera reporteViandasHeladera: reporteList)
            this.repositorioViandasHeladera.persistir(reporteViandasHeladera);
        List<ViandasDonadasPorColaborador> viandasList = this.obtenerViandasPorColaborador(fechaActual);
        for (ViandasDonadasPorColaborador viandasDonadasPorColaborador: viandasList)
            this.repositorioViandasColaborador.persistir(viandasDonadasPorColaborador);
    }

    private List<ViandasDonadasPorColaborador> obtenerViandasPorColaborador(LocalDate fecha) {
        List<Colaborador> colaboradores = this.colaboradorRepositorio.buscarTodos(Colaborador.class);
        List<ViandasDonadasPorColaborador> viandasDonadasPorColaboradorList = new ArrayList<>();
        for(Colaborador colaborador: colaboradores) {
            ViandasDonadasPorColaborador viandasDonadasPorColaborador = new ViandasDonadasPorColaborador(colaborador, fecha);
            viandasDonadasPorColaborador.viandasDonadasSemanal();

            viandasDonadasPorColaboradorList.add(viandasDonadasPorColaborador);
        }
        return viandasDonadasPorColaboradorList;
    }

    private List<ReporteViandasHeladera> obtenerViandasPorHeladera(LocalDate fecha) {
        List<Heladera> heladeras = this.heladerasRepositorio.buscarTodas();
        List<ReporteViandasHeladera> reporteViandasHeladerasLista = new ArrayList<>();
        for(Heladera heladera: heladeras) {
            ReporteViandasHeladera reporteViandasHeladera = new ReporteViandasHeladera(heladera, fecha);
            reporteViandasHeladera.viandasColocadasSemanal();
            reporteViandasHeladera.viandasRetiradasSemanal();

            reporteViandasHeladerasLista.add(reporteViandasHeladera);
        }
        return reporteViandasHeladerasLista;
    }

    private List<FallosPorHeladera> obtenerFallosPorHeladera(LocalDate fecha) {
        List<Heladera> heladeras = this.heladerasRepositorio.buscarTodas();
        List<FallosPorHeladera> fallosPorHeladerasLista = new ArrayList<>();
        for(Heladera heladera: heladeras) {
            FallosPorHeladera fallosPorHeladera = new FallosPorHeladera(fecha);
            fallosPorHeladera.setHeladera(heladera);
            fallosPorHeladera.fallosSemanalesHeladera();

            fallosPorHeladerasLista.add(fallosPorHeladera);
        }
        return fallosPorHeladerasLista;
    }
}



