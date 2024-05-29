package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Distribucion extends TipoColaboracion{
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidadViandas;
    private Motivo motivo;
    private static Double factorDePuntaje;

    public Distribucion() {
        setNombreTipo("REDISTRIBUCION_VIANDAS");
        factorDePuntaje = 1.0;
        this.cuentaParaPuntaje = true;
    }

    @Override
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
