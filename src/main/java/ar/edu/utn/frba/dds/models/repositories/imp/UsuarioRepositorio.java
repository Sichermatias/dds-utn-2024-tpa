package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.util.List;

public class UsuarioRepositorio extends BaseRepositorio<Usuario> implements WithSimplePersistenceUnit {

    private static UsuarioRepositorio instancia;

    public static UsuarioRepositorio getInstancia(){
        if (instancia == null){
            instancia = new UsuarioRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }

    public Usuario usuarioRegistrado(Usuario usuario, List<Usuario> usuarios){
        Usuario usuarioFinal = null;

        for (Usuario usuario1 : usuarios) {
            if (usuario1.getNombreUsuario().equals(usuario.getNombreUsuario()) && usuario1.getContrasenia().equals(usuario.getContrasenia())) {
                usuarioFinal = usuario1;
            }
        }
        return usuarioFinal;
    }

}