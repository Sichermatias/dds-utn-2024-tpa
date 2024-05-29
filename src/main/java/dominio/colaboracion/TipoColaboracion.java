package dominio.colaboracion;

import lombok.Getter;
import lombok.Setter;

public abstract class TipoColaboracion {
    private String nombreTipo;
    protected boolean cuentaParaPuntaje;

    static {
        System.out.println("Inicializando registros de tipos de colaboración en bloque estático");
        TipoColaboracionRegistry.register("DINERO", DonacionDeDinero::new);
        TipoColaboracionRegistry.register("DONACION_VIANDAS", DonacionVianda::new);
        TipoColaboracionRegistry.register("REDISTRIBUCION_VIANDAS", Distribucion::new);
        TipoColaboracionRegistry.register("ENTREGA_TARJETAS", RegistrarPersonaVulnerable::new);
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public abstract Double puntaje();
}
