package dominio.colaboracion;

public class OfrecerPremio{
    private PremioCatalogo premioCatalogo;
    private Colaboracion colaboracion;
    private boolean cuentaParaPuntaje;

    public OfrecerPremio() {
        this.colaboracion = new Colaboracion();
        this.cuentaParaPuntaje = false;
    }
    public Double puntaje() {
        return 0.0;
    }
}
