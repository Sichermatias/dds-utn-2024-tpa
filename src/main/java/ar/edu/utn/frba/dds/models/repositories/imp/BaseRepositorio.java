package ar.edu.utn.frba.dds.models.repositories.imp;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


public abstract class BaseRepositorio<T> {
    protected abstract EntityManager getEntityManager();

    public void persistir(T entidad) {
            getEntityManager().persist(entidad);
        }

    public void actualizar(T entidad) {
        getEntityManager().merge(entidad);
        }

    public void borrar(T entidad) {
        getEntityManager().remove(entidad);
        }

    public T buscarPorId(Class<T> claseEntidad, long id) {
        return getEntityManager().find(claseEntidad, id);
    }

    public List<T> buscarTodos(Class<T> claseEntidad) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }
    public List<T> buscarPorNombre(Class<T> claseEntidad, String nombre) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("nombre"), nombre); // Asume que el campo se llama "nombre"
        cq.where(condicion);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> buscarPorEstado(Class<T> claseEntidad, String estado) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        // Assuming "estado" is an enum and the field in the entity is a string
        Predicate condicion = cb.equal(root.get("estado").as(Integer.class), estado);
        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }
}
