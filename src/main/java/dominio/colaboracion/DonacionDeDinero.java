package dominio.colaboracion;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DonacionDeDinero extends TipoColaboracion{
    private Double monto;
    private Frecuencia frecuencia;
    private static Double factorDePuntaje;

    public DonacionDeDinero() {
        setNombreTipo("DINERO");
        factorDePuntaje = 0.5;
        this.cuentaParaPuntaje = true;
    }

    @Override
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
