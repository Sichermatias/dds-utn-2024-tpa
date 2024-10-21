package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ColaboracionRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
        private static ColaboracionRepositorio instancia;
        public static ColaboracionRepositorio getInstancia(){
            if (instancia == null){
            instancia = new ColaboracionRepositorio();
            }
            return instancia;
        }
        @Override
        protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<Colaboracion> obtenerColaboracionesPorColaboradorId(Long colaboradorId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Colaboracion> cq = cb.createQuery(Colaboracion.class);
        Root<Colaboracion> root = cq.from(Colaboracion.class);

        Predicate condicion = cb.equal(root.get("colaborador").get("id"), colaboradorId);
        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }

}