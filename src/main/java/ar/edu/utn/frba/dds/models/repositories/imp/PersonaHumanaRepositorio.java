package ar.edu.utn.frba.dds.models.repositories.imp;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
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
        /*
        TODO 2024-09-11: rompe porque colaborador ya no tiene colaboracionOptional<Colaborador> personaExistente = buscarPorDNI(colaboradorModificada.getNroDocumento());
        personaExistente.ifPresentOrElse(
                personaEncontrada -> personaEncontrada.agregarColaboraciones(colaboradorModificada.getColaboraciones()),
                () -> {
                    throw new IllegalArgumentException("La persona no existe en el repositorio");
                }
        );
     */
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