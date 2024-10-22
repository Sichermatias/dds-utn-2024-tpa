package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.reportes.FallosPorHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class FallosHeladeraRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit{
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

    public List<FallosPorHeladera> buscarTodas(){
        return buscarTodos(FallosPorHeladera.class);
    }
}
