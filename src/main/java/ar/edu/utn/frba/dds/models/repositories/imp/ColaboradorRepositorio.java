package ar.edu.utn.frba.dds.models.repositories.imp;
import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ColaboradorRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
    private static ColaboradorRepositorio instancia;
    private List<Colaborador> colaboradores;

    public void agregar(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

    public Colaborador obtenerColaboradorPorUsuarioId(Long usuarioId) {
        ColaboradorRepositorio colaboradorRepositorio = new ColaboradorRepositorio();
        List<Colaborador> colaboradores = colaboradorRepositorio.buscarPorUsuarioId(Colaborador.class,usuarioId);
        return colaboradores.isEmpty() ? null : colaboradores.get(0);
    }

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
