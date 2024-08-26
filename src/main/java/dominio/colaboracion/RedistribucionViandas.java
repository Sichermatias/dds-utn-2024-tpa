package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RedistribucionViandas {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidadViandas;
    private Motivo motivo;
    private static Double factorDePuntaje;
    private Colaboracion colaboracion;
    private boolean cuentaParaPuntaje;
    public RedistribucionViandas() {
        this.colaboracion = new Colaboracion();
        factorDePuntaje = 1.0;
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
