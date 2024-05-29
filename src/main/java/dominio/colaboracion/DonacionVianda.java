package dominio.colaboracion;

public class DonacionVianda extends TipoColaboracion{
    private Vianda vianda;
    private static Double factorDePuntaje;

    public DonacionVianda() {
        setNombreTipo("DONACION_VIANDAS");
        factorDePuntaje = 1.5;
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
