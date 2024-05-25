package dominio.colaboracion;

import java.time.LocalDate;

public class DonacionDeDinero extends TipoColaboracion{
    public DonacionDeDinero() {
        setNombreTipo("DINERO");
    }
    private Double monto;
    private Frecuencia frecuencia;
}
