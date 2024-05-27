package dominio.colaboracion;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Colaboracion {
    private String nombre;
    private String descripcion;
    @Setter@Getter
    private LocalDate fechaColaboracion;
    private TipoColaboracion tipoColaboracion;

    public void cambiarTipoColaboracion(TipoColaboracion newTipoColaboracion){
        tipoColaboracion = newTipoColaboracion;
    }

}
