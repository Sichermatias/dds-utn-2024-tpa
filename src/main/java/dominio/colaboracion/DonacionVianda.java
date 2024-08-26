package dominio.colaboracion;

import dominio.persona.Colaborador;
import lombok.Getter;
import lombok.Setter;

public class DonacionVianda{
    private Vianda vianda;
    private static Double factorDePuntaje;
    @Setter @Getter
    private Colaboracion colaboracion;
    private boolean cuentaParaPuntaje;
    public DonacionVianda() {
        this.colaboracion = new Colaboracion();
        factorDePuntaje = 1.5;
        this.cuentaParaPuntaje = true;
    }
    public Double puntaje() {
        Double resultado;
        if(cuentaParaPuntaje) {
            resultado = factorDePuntaje;
            this.cuentaParaPuntaje = false;
        } else {
            resultado = 0.0;
        }
        return resultado;
    }
}
