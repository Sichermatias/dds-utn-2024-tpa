package ar.edu.utn.frba.dds.dominio.colaboracion;

import ar.edu.utn.frba.dds.dominio.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "donacionDinero")
@Setter @Getter
public class DonacionDinero extends Persistente{
    @Column(name = "monto")
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "frecuencia")
    private Frecuencia frecuencia;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaboracion_id")
    private Colaboracion colaboracion;

    public DonacionDinero() {
        this.colaboracion = new Colaboracion();
    }
    public Double puntaje(){
        return this.monto*0.5;
    }
}
