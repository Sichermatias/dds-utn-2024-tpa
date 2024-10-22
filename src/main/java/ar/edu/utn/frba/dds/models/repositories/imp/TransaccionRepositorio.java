package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class TransaccionRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
        private static TransaccionRepositorio instancia;
        public static TransaccionRepositorio getInstancia(){
            if (instancia == null){
            instancia = new TransaccionRepositorio();
            }
            return instancia;
        }
        @Override
        protected EntityManager getEntityManager() {
        return entityManager();
    }


}