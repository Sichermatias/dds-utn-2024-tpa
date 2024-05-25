package dominio.colaboracion;

import dominio.infraestructura.Heladera;
@Setter @Getter
public class Distribucion extends TipoColaboracion{
    public Distribucion() {
        setNombreTipo("REDISTRIBUCION_VIANDAS");
    }
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidadViandas;
    private Motivo motivo;
}
