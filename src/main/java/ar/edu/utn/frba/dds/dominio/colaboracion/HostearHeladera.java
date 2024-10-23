package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "hostearHeladera")
@Getter @Setter
public class HostearHeladera extends Persistente {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaboracion_id")
    private Colaboracion colaboracion;

    @Column(name = "enVigencia", columnDefinition = "BIT(1)")
    private Boolean enVigencia;

    public HostearHeladera() {
        this.colaboracion = new Colaboracion();
    }
    public Double puntaje() {
        LocalDate fechaActual = LocalDate.now();

        long mesesActiva = ChronoUnit.MONTHS.between(this.heladera.getFechaPuestaEnMarcha(), fechaActual);

        return mesesActiva * 5.0;
    }
}
