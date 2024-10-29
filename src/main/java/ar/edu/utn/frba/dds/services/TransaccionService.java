package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dominio.colaboracion.Transaccion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;

public class TransaccionService {

    public Transaccion crearTransaccion(Colaborador colaborador, double puntaje) {
        Transaccion transaccion = new Transaccion();
        transaccion.setColaborador(colaborador);
        transaccion.setMontoPuntaje(puntaje);
        return transaccion;
    }
}