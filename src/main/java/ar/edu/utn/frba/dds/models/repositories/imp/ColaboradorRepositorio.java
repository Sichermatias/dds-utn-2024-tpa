package ar.edu.utn.frba.dds.models.repositories.imp;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Optional;

public class ColaboradorRepositorio extends BaseRepositorio<Colaborador> implements WithSimplePersistenceUnit {
    private static ColaboradorRepositorio instancia;

    public static ColaboradorRepositorio getInstancia(){
        if (instancia == null){
            instancia = new ColaboradorRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

}
