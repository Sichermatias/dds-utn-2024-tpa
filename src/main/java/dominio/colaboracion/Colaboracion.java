package dominio.colaboracion;

import java.time.LocalDate;

public class Colaboracion {
    private String nombre;
    private String descripcion;
    private LocalDate fechaColaboracion;
    private TipoColaboracion tipoColaboracion;

    public void cambiarTipoColaboracion(TipoColaboracion newTipoColaboracion){
        tipoColaboracion = newTipoColaboracion;
    }

}
