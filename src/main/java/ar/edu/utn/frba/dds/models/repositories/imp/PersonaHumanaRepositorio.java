package ar.edu.utn.frba.dds.models.repositories.imp;
import dominio.persona.Colaborador;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Optional;

public class  PersonaHumanaRepositorio {
    @Getter
    private ArrayList<Colaborador> colaboradors;
    private static PersonaHumanaRepositorio instancia;
    public static synchronized PersonaHumanaRepositorio obtenerInstancia() {
        if (instancia == null) {
            instancia = new PersonaHumanaRepositorio();
        }
        return instancia;
    }
    public PersonaHumanaRepositorio() {
        this.colaboradors = new ArrayList<>();
    }

    public void agregar(Colaborador colaborador) {
        colaboradors.add(colaborador);
    }

    public void actualizar(Colaborador colaboradorModificada) {
        Optional<Colaborador> personaExistente = buscarPorDNI(colaboradorModificada.getNroDocumento());
        personaExistente.ifPresentOrElse(
                personaEncontrada -> personaEncontrada.agregarColaboraciones(colaboradorModificada.getColaboraciones()),
                () -> {
                    throw new IllegalArgumentException("La persona no existe en el repositorio");
                }
        );
    }
    public void eliminar(Colaborador colaborador) {
        colaboradors.remove(colaborador);
    }

    public Optional<Colaborador> buscarPorDNI(int dni) {
        return colaboradors.stream()
                .filter(persona -> persona.getNroDocumento().equals(dni))
                .findFirst();
    }
}