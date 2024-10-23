package ar.edu.utn.frba.dds.models.repositories.imp;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class DonacionDineroRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
        private static DonacionDineroRepositorio instancia;
        public static DonacionDineroRepositorio getInstancia(){
            if (instancia == null){
            instancia = new DonacionDineroRepositorio();
            }
            return instancia;
        }
        @Override
        protected EntityManager getEntityManager() {
        return entityManager();
    }

}