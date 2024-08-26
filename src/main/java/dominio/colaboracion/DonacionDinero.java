package dominio.colaboracion;

import dominio.persona.Colaborador;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DonacionDinero{
    private Double monto;
    private Frecuencia frecuencia;
    private static Double factorDePuntaje;
    private Colaboracion colaboracion;
    private boolean cuentaParaPuntaje;
    public DonacionDinero() {
        this.colaboracion = new Colaboracion();
        factorDePuntaje = 0.5;
        this.cuentaParaPuntaje = true;
    }
    public Double puntaje() {
        double resultado;
        if(cuentaParaPuntaje) {
            resultado = factorDePuntaje * this.monto;
            this.cuentaParaPuntaje = false;
        } else {
            resultado = 0.0;
        }
        return resultado;
    }
}
