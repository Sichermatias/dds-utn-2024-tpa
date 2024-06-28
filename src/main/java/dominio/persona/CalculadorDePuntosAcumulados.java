package dominio.persona;

import dominio.colaboracion.Colaboracion;

public class CalculadorDePuntosAcumulados {
    public double calcularPuntosDeColaborador(Colaborador colaborador) {
        return colaborador.getColaboraciones().stream().mapToDouble(Colaboracion::puntaje).sum();
    }
}
