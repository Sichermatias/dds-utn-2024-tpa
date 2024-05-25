package dominio.repositories;
import dominio.persona.Persona;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonaHumanaRepositorio {
    private List<Persona> personas;

    public PersonaHumanaRepositorio() {
        this.personas = new ArrayList<>();
    }

    public void agregar(Persona persona) {
        personas.add(persona);
    }

    public void modificar(Persona personaModificada) {
        Optional<Persona> personaExistente = buscarPorDNI(personaModificada.getNroDocumento());
        personaExistente.ifPresentOrElse(
                persona -> {
                    int indice = personas.indexOf(persona);
                    personas.set(indice, personaModificada);
                },
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