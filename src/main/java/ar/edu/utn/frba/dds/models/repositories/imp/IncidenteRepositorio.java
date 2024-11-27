package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class IncidenteRepositorio extends BaseRepositorio<Incidente> implements WithSimplePersistenceUnit {

    private static IncidenteRepositorio instancia;
    public static IncidenteRepositorio getInstancia(){
        if (instancia == null){
            instancia = new IncidenteRepositorio();
        }
        return instancia;
    }

    public List<Incidente> buscarPorHeladeraId(Long heladeraId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Incidente> cq = cb.createQuery(Incidente.class);
        Root<Incidente> root = cq.from(Incidente.class);

        Predicate condicion = cb.equal(root.get("heladeraIncidente").get("id"), heladeraId);
        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
