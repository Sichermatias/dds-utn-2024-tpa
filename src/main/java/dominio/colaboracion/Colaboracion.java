package dominio.colaboracion;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Colaboracion {
    private String nombre;
    private String descripcion;

    public LocalDate fechaColaboracion;
    private TipoColaboracion tipoColaboracion;

    public void cambiarTipoColaboracion(TipoColaboracion newTipoColaboracion){
        tipoColaboracion = newTipoColaboracion;
    }
    public void setFechaColaboracion(String fecha){
        fechaColaboracion=LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
