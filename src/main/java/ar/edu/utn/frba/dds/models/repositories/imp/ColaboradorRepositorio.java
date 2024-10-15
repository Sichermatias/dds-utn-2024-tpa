package ar.edu.utn.frba.dds.models.repositories.imp;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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

    public Colaborador buscarPorIdUsuario(Long idUsuario){
        String queryString = "SELECT c FROM Colaborador c WHERE c.usuario.id = :idUsuario and c.activo = true";
        TypedQuery<Colaborador> query = entityManager().createQuery(queryString, Colaborador.class);
        query.setParameter("idUsuario", idUsuario);
        return query.getResultList().get(0);
    }

}
