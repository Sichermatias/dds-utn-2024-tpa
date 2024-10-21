package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class HeladerasRepositorio  extends BaseRepositorio implements WithSimplePersistenceUnit{

    private static HeladerasRepositorio instancia;
    public static HeladerasRepositorio getInstancia(){
        if (instancia == null){
            instancia = new HeladerasRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<Heladera> buscarTodas(){
        return buscarTodos(Heladera.class);
    }
}
