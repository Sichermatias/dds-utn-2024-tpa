package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.reportes.ReporteViandasHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ViandasHeladeraReporteRepositorio extends BaseRepositorio<ReporteViandasHeladera> implements WithSimplePersistenceUnit {
    private static ViandasHeladeraReporteRepositorio instancia;
    public static ViandasHeladeraReporteRepositorio getInstancia(){
        if (instancia == null){
            instancia = new ViandasHeladeraReporteRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<ReporteViandasHeladera> buscarTodas(){
        return buscarTodos(ReporteViandasHeladera.class);
    }

    public List<ReporteViandasHeladera> buscarUltimos7Dias() {
        // Calculamos la fecha límite
        LocalDate fechaLimite = LocalDate.now().minus(7, ChronoUnit.DAYS);

        // Creamos la consulta
        TypedQuery<ReporteViandasHeladera> query = this.getEntityManager().createQuery(
                "SELECT rvh FROM ReporteViandasHeladera rvh " +
                        "WHERE rvh.fechaDeReporteSemanal >= :fechaLimite",
                ReporteViandasHeladera.class);

        // Asignamos el parámetro de la fecha límite
        query.setParameter("fechaLimite", fechaLimite);

        return query.getResultList();
    }
}
