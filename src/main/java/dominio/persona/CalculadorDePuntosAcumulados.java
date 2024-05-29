package dominio.persona;

import dominio.colaboracion.Colaboracion;

public class CalculadorDePuntosAcumulados {
    public double calcularPuntosDeColaborador(Persona persona) {
        return persona.getColaboraciones().stream().mapToDouble(Colaboracion::puntaje).sum();
    }
}
