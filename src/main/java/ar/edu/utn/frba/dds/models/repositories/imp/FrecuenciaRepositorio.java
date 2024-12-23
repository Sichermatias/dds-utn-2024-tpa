package ar.edu.utn.frba.dds.models.repositories.imp;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class FrecuenciaRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
        private static FrecuenciaRepositorio instancia;
        public static FrecuenciaRepositorio getInstancia(){
            if (instancia == null){
            instancia = new FrecuenciaRepositorio();
            }
            return instancia;
        }
        @Override
        protected EntityManager getEntityManager() {
        return entityManager();
    }

}