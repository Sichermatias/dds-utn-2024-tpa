package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class LocalidadRepositorio extends BaseRepositorio<Localidad> implements WithSimplePersistenceUnit {

    private static LocalidadRepositorio instancia;
    public static LocalidadRepositorio getInstancia(){
        if (instancia == null){
            instancia = new LocalidadRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<Localidad> buscarPorNombre(Class<Localidad> claseEntidad, String nombre) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Localidad> cq = cb.createQuery(claseEntidad);
        Root<Localidad> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("nombreLocalidad"), nombre);
        cq.where(condicion);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }

}
