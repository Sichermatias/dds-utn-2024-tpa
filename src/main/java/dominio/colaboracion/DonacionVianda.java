package dominio.colaboracion;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class DonacionVianda{
    private Vianda vianda;
    private static Double factorDePuntaje;
    @OneToOne
    @JoinColumn(name = "colaboracion_id")
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
