package dominio.repositories;
import dominio.persona.Persona;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Optional;

public class  PersonaHumanaRepositorio {
    @Getter
    private ArrayList<Persona> personas;
    private static PersonaHumanaRepositorio instancia;
    public static synchronized PersonaHumanaRepositorio obtenerInstancia() {
        if (instancia == null) {
            instancia = new PersonaHumanaRepositorio();
        }
        return instancia;
    }
    public PersonaHumanaRepositorio() {
        this.personas = new ArrayList<>();
    }

    public void agregar(Persona persona) {
        personas.add(persona);
    }

    public void actualizar(Persona personaModificada) {
        Optional<Persona> personaExistente = buscarPorDNI(personaModificada.getNroDocumento());
        personaExistente.ifPresentOrElse(
                personaEncontrada -> personaEncontrada.agregarColaboraciones(personaModificada.getColaboraciones()),
                () -> {
                    throw new IllegalArgumentException("La persona no existe en el repositorio");
                }
        );
    }
    public void eliminar(Persona persona) {
        personas.remove(persona);
    }

    public Optional<Persona> buscarPorDNI(int dni) {
        return personas.stream()
                .filter(persona -> persona.getNroDocumento().equals(dni))
                .findFirst();
    }
}