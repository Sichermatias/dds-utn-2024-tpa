package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dominio.colaboracion.Transaccion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;

import java.time.LocalDateTime;

public class TransaccionService {

    public Transaccion crearTransaccion(Colaborador colaborador, double puntaje) {
        Transaccion transaccion = new Transaccion();
        transaccion.setColaborador(colaborador);
        transaccion.setFechaHoraAlta(LocalDateTime.now());
        transaccion.setMontoPuntaje(puntaje);
        colaborador.actualizarPuntajeSumandole(puntaje);
        return transaccion;
    }
}