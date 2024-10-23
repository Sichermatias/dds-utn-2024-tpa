package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.reportes.ViandasDonadasPorColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class ViandasDonadasColaboradorRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
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
}
