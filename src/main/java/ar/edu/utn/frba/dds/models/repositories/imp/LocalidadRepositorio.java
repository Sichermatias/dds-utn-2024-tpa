package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;

public class LocalidadRepositorio extends BaseRepositorio<Localidad> implements WithSimplePersistenceUnit {

    private static LocalidadRepositorio instancia;
    public static LocalidadRepositorio getInstancia(){
        if (instancia == null){
            instancia = new LocalidadRepositorio();
        }
        return instancia;
    }
    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
