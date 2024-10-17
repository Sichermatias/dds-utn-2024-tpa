package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;

import javax.persistence.EntityManager;
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

}