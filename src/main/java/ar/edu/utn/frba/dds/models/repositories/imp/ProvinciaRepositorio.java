package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProvinciaRepositorio extends BaseRepositorio<Provincia> implements WithSimplePersistenceUnit {

    private static ProvinciaRepositorio instancia;
    public static ProvinciaRepositorio getInstancia(){
        if (instancia == null){
            instancia = new ProvinciaRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<Provincia> buscarPorNombre(Class<Provincia> claseEntidad, String nombre) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Provincia> cq = cb.createQuery(claseEntidad);
        Root<Provincia> root = cq.from(claseEntidad);

        Predicate condicion = cb.equal(root.get("nombreProvincia"), nombre);
        cq.where(condicion);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }

}
