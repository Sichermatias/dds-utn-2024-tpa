package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.colaboracion.Premio;
import ar.edu.utn.frba.dds.dominio.colaboracion.RubroPremio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PremioRepositorio extends BaseRepositorio<Premio> implements WithSimplePersistenceUnit {
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<String> buscarTodosLosRubros() {
        TypedQuery<RubroPremio> query = entityManager()
                .createQuery("from " + RubroPremio.class.getName(), RubroPremio.class);
        return query.getResultList().stream().map(RubroPremio::getNombre).toList();
    }

}
