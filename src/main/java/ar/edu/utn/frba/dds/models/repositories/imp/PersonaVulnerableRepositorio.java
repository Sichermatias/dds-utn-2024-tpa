package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.persona.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class PersonaVulnerableRepositorio extends BaseRepositorio<PersonaVulnerable> implements WithSimplePersistenceUnit {

    private static PersonaVulnerableRepositorio instancia;

    public static PersonaVulnerableRepositorio getInstancia(){
        if (instancia == null){
            instancia = new PersonaVulnerableRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public void PersistirPersonaVulnerable(PersonaVulnerable personaVulnerable){
        persistir(personaVulnerable);
    }

}