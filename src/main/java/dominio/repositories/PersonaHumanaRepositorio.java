package dominio.repositories;
import dominio.persona.Persona;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class  PersonaHumanaRepositorio {
    private List<Persona> personas;
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