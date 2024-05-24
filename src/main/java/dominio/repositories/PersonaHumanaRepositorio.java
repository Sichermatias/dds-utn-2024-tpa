package dominio.repositories;
import dominio.persona.PersonaHumana;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonaHumanaRepositorio {
    private List<PersonaHumana> personas;

    public PersonaHumanaRepositorio() {
        this.personas = new ArrayList<>();
    }

    public void agregar(PersonaHumana persona) {
        personas.add(persona);
    }

    public void modificar(PersonaHumana personaModificada) {
        Optional<PersonaHumana> personaExistente = buscarPorDNI(personaModificada.getDocumento());
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
    public void eliminar(PersonaHumana persona) {
        personas.remove(persona);
    }

    public Optional<PersonaHumana> buscarPorDNI(int dni) {
        return personas.stream()
                .filter(persona -> persona.getDocumento().equals(dni))
                .findFirst();
    }
}