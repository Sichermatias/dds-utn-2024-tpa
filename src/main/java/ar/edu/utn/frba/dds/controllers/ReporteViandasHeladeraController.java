package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.reportes.ReporteViandasHeladera;
import ar.edu.utn.frba.dds.dominio.services.cronjobs.tasks.GenerarReportesSemanales;
import ar.edu.utn.frba.dds.models.repositories.imp.HeladerasRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ViandasHeladeraReporteRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteViandasHeladeraController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    public ReporteViandasHeladeraController() {
    }

    @Override
    public void create(Context context) {

    }

    public ReporteViandasHeladera buscarOCrearReportePorHeladera(Heladera heladera) {
        ViandasHeladeraReporteRepositorio repo = ViandasHeladeraReporteRepositorio.getInstancia();
        List<ReporteViandasHeladera> reportes =repo.buscarPorHeladeraId(ReporteViandasHeladera.class, heladera.getId());
        ReporteViandasHeladera reporte;
        if (reportes.isEmpty()) {
            reporte = new ReporteViandasHeladera(heladera, LocalDate.now());
            reporte.viandasColacadasSemanal();
            reporte.viandasRetiradasSemanal();
            repo.persistir(reporte);
        }else{
            reporte=reportes.get(0);
            reporte.viandasColacadasSemanal();
            reporte.viandasRetiradasSemanal();
            repo.persistir(reporte);
        }

        return reporte;
    }

    @Override
    public void index(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        if (usuarioId == null) {
            context.redirect("/login");
            return;
        }

        String tipoRol = context.sessionAttribute("tipo_rol");
        Map<String, Object> model = new HashMap<>();
        model.put("usuario_id", usuarioId);
        model.put("tipo_rol", tipoRol);

        List<Heladera> heladeras = HeladerasRepositorio.getInstancia().buscarTodas();
        List<ReporteViandasHeladera> viandasPorHeladera = new ArrayList<>();

        for (Heladera heladera : heladeras) {
            ReporteViandasHeladera reporte = buscarOCrearReportePorHeladera(heladera);
            viandasPorHeladera.add(reporte);
        }

        model.put("viandasPorHeladera", viandasPorHeladera);
        context.render("viandasHeladera/viandas_heladera.hbs", model);
    }

    @Override
    public void show(Context context){

    }
    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

}
