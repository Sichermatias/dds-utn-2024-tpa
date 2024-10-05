package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "colaboracion")
public class Colaboracion extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "tipo", columnDefinition = "VARCHAR(50)")
    @Setter @Getter
    private String tipo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fechaColaboracion", columnDefinition = "DATE")
    public LocalDate fechaColaboracion;

    @OneToOne
    @JoinColumn(name = "transaccion_id")
    private Transaccion transaccion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
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
