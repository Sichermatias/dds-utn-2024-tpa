package ar.edu.utn.frba.dds.models.repositories.imp;
import dominio.infraestructura.Heladera;
import dominio.infraestructura.Suscripcion;

import java.util.ArrayList;
import java.util.Optional;
public class HeladeraRepositorio {
    private ArrayList<Heladera> heladeras;
    private static HeladeraRepositorio instancia;
    public static synchronized HeladeraRepositorio obtenerInstancia() {
        if (instancia == null) {
            instancia = new HeladeraRepositorio();
        }
        return instancia;
    }
    public HeladeraRepositorio() {
        this.heladeras = new ArrayList<>();
    }
    public void agregar(Heladera heladera){heladeras.add(heladera);}
    public void eliminar(Heladera heladera){heladeras.remove(heladera);}
    public void actualizar(Heladera heladeraModificada) {
        Optional<Heladera> heladeraExistente = buscarPorNombre(heladeraModificada.getNombre());
        heladeraExistente.ifPresentOrElse(
                heladeraEncontrada -> heladeraEncontrada.agregarSuscripcion((Suscripcion) heladeraModificada.getSuscripciones()),
                () -> {
                    throw new IllegalArgumentException("La heladera no existe en el repositorio");
                }
        );
    }
    public Optional<Heladera> buscarPorNombre(String nombreheladera) {
        return heladeras.stream()
                .filter(heladera -> heladera.getNombre().equals(nombreheladera))
                .findFirst();
    }
}
