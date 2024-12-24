package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.infraestructura.Modelo;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ModeloRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
    private static ModeloRepositorio instancia;
    public static ModeloRepositorio getInstancia(){
        if (instancia == null){
            instancia = new ModeloRepositorio();
        }
        return instancia;
    }
    public List<Modelo> buscarPorNombre(String nombre) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Modelo> cq = cb.createQuery(Modelo.class);
        Root<Modelo> root = cq.from(Modelo.class);

        Predicate condicion = cb.equal(root.get("nombre"), nombre);
        cq.where(condicion);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
