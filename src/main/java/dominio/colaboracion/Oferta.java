package dominio.colaboracion;

public class Oferta extends TipoColaboracion {
    private Premio premio;

    public Oferta() {
        this.cuentaParaPuntaje = false;
    }

    @Override
    public Double puntaje() {
        return 0.0;
    }
}
