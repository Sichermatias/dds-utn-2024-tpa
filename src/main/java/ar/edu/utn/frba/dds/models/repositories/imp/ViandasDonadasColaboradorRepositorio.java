package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.reportes.ViandasDonadasPorColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ViandasDonadasColaboradorRepositorio extends BaseRepositorio<ViandasDonadasPorColaborador> implements WithSimplePersistenceUnit {
    private static ViandasDonadasColaboradorRepositorio instancia;
    public static ViandasDonadasColaboradorRepositorio getInstancia(){
        if (instancia == null){
            instancia = new ViandasDonadasColaboradorRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<ViandasDonadasPorColaborador> buscarTodas(){
        return buscarTodos(ViandasDonadasPorColaborador.class);
    }

    public List<ViandasDonadasPorColaborador> buscarUltimos7Dias() {
        // Calculamos la fecha límite
        LocalDate fechaLimite = LocalDate.now().minus(7, ChronoUnit.DAYS);

        // Creamos la consulta
        TypedQuery<ViandasDonadasPorColaborador> query = this.getEntityManager().createQuery(
                "SELECT vdc FROM ViandasDonadasPorColaborador vdc " +
                        "WHERE vdc.fechaDeReporteSemanal >= :fechaLimite",
                ViandasDonadasPorColaborador.class);

        // Asignamos el parámetro de la fecha límite
        query.setParameter("fechaLimite", fechaLimite);

        return query.getResultList();
    }
}
