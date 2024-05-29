package dominio.colaboracion;

public class DistribucionTarjetas extends TipoColaboracion{
    private static Double factorDePuntaje = 2.0;

    public DistribucionTarjetas() {
        setNombreTipo("ENTREGA_TARJETAS");
        factorDePuntaje = 2.0;
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
