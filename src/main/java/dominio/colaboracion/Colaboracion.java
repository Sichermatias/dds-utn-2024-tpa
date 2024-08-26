package dominio.colaboracion;

import dominio.persona.Colaborador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Colaboracion {
    private String nombre;
    private String descripcion;

    public LocalDate fechaColaboracion;
    private Transaccion transaccion;
    @Setter @Getter
    private Colaborador colaborador;


    public void setFechaColaboracion(String fecha){
        fechaColaboracion=LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    //TODO adaptar el puntaje a la composicion ya no hay tipoColaboracion
    public Double puntaje() {
        return 0.0; //tipoColaboracion.puntaje();
    }
}
