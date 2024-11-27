package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.reportes.FallosPorHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FallosHeladeraRepositorio extends BaseRepositorio<FallosPorHeladera> implements WithSimplePersistenceUnit{
    private static FallosHeladeraRepositorio instancia;
    public static FallosHeladeraRepositorio getInstancia(){
        if (instancia == null){
            instancia = new FallosHeladeraRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<FallosPorHeladera> buscarUltimos7Dias() {
        // Calculamos la fecha límite
        LocalDate fechaLimite = LocalDate.now().minus(7, ChronoUnit.DAYS);

        // Creamos la consulta
        TypedQuery<FallosPorHeladera> query = this.getEntityManager().createQuery(
                "SELECT fph FROM FallosPorHeladera fph " +
                        "WHERE fph.fechaDeReporteSemanal >= :fechaLimite",
                FallosPorHeladera.class);

        // Asignamos el parámetro de la fecha límite
        query.setParameter("fechaLimite", fechaLimite);

        return query.getResultList();
    }
}
