package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.reportes.ReporteViandasHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class ViandasHeladeraReporteRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
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
}
