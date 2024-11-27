package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.colaboracion.PedidoDeApertura;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.hibernate.mapping.List;

import javax.persistence.EntityManager;

public class PedidoDeAperturaRepositorio  extends BaseRepositorio<PedidoDeApertura> implements WithSimplePersistenceUnit {

    public PedidoDeApertura buscarPorHeladeraYTarjetaId(Integer idHeladera, Long idTarjeta) {
        return super.buscarPorHeladeraYTarjetaId(PedidoDeApertura.class, idHeladera, idTarjeta).get(0);//Trae el primero que encuentre que deberia ser el ultimo. Con problemas de concurrencia deberiamos filtrar bien para q traiga x fehca o x id del pedido
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager();
    }
}
