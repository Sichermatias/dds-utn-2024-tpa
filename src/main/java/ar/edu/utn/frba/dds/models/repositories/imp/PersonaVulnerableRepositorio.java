package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    public void PersistirPersonaVulnerable(PersonaVulnerable personaVulnerable) {
        persistir(personaVulnerable);
    }

    public PersonaVulnerable buscarPorIdVulnerable(Long idPersonaVulnerable){
        String queryString = "SELECT c FROM PersonaVulnerable c WHERE c.id = :idPersonaVulnerable and c.activo = true";
        TypedQuery<PersonaVulnerable> query = entityManager().createQuery(queryString, PersonaVulnerable.class);
        query.setParameter("idPersonaVulnerable", idPersonaVulnerable);
        return query.getResultList().get(0);
    }

}