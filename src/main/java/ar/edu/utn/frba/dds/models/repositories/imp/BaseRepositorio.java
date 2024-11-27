package ar.edu.utn.frba.dds.models.repositories.imp;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class BaseRepositorio<T> {
    protected abstract EntityManager getEntityManager();

    public List<T> buscarPorUsuarioId(Class<T> claseEntidad, Long usuarioId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("usuario").get("id"), usuarioId);
        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> buscarPorHeladeraId(Class<T> claseEntidad, Long heladeraId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("heladera").get("id"), heladeraId);
        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> buscarPorHeladeraYTarjetaId(Class<T> claseEntidad, Integer heladeraId, Long tarjetaId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicionHeladera = cb.equal(root.get("heladera").get("id"), heladeraId);
        Predicate condicionTarjeta = cb.equal(root.get("tarjeta").get("id"), tarjetaId);
        Predicate condicionFinal = cb.and(condicionHeladera, condicionTarjeta);

        cq.where(condicionFinal);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> buscarIncidentePorHeladeraId(Class<T> claseEntidad, Long heladeraId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("heladeraIncidente").get("id"), heladeraId);

        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }


    public List<T> buscarPorColaboradorId(Class<T> claseEntidad, Long colaboradorId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("colaborador").get("id"), colaboradorId);
        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }

    public void persistir(T entidad) {
        EntityTransaction transaction = null;
        try {
            transaction = getEntityManager().getTransaction();
            transaction.begin();
            getEntityManager().persist(entidad);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void actualizar(T entidad) {
        EntityTransaction transaction = null;
        try {
            transaction = getEntityManager().getTransaction();
            transaction.begin();
            getEntityManager().merge(entidad);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void borrar(T entidad) {
        EntityTransaction transaction = null;
        try {
            transaction = getEntityManager().getTransaction();
            transaction.begin();
            getEntityManager().remove(entidad);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
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

        Predicate condicion = cb.equal(root.get("nombreUsuario"), nombre);
        cq.where(condicion);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> buscarPorRol(Class<T> claseEntidad, String nombre) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("nombreRol"), nombre);
        cq.where(condicion);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> buscarPorDNI(Class<T> claseEntidad, String dni) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("nroDocumento"), dni);
        cq.where(condicion);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> buscarPorEstado(Class<T> claseEntidad, String estado) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("estado").as(Integer.class), estado);
        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }
}
