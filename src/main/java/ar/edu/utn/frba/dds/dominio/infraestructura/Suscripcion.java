package ar.edu.utn.frba.dds.dominio.infraestructura;

import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "suscripcion")
@Setter @Getter
public class Suscripcion {

    public Suscripcion(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Embedded
    private FiltroSuscripcion filtro;

    @Transient
    private Mensajero mensajero;

    @Column(name = "contacto", columnDefinition = "VARCHAR(100)")
    @Getter@Setter
    private String contacto;

    public Suscripcion(Colaborador colaborador, Heladera heladera, FiltroSuscripcion filtro, Mensajero mensajero, String contacto) {
        this.colaborador = colaborador;
        this.heladera = heladera;
        this.filtro = filtro;
        this.mensajero = mensajero;
        this.contacto = contacto;
    }
    public void notificarColaborador(){
        Mensaje mensaje= new Mensaje(contacto, generarMensaje());
        mensajero.enviarMensaje(mensaje);
    }
    private String generarMensaje() {
        StringBuilder mensajeBuilder = new StringBuilder("La heladera " + heladera.getId());
        if (filtro.getViandasParaLlenar() != null && heladera.getViandasParaLlenar() <= filtro.getViandasParaLlenar()) {
            mensajeBuilder.append(" está a punto de llenarse.");
        }
        if (filtro.getMinViandas() != null && heladera.getCantViandasActuales() <= filtro.getMinViandas()) {
            mensajeBuilder.append(" está a punto de vaciarse.");
        }
        if (filtro.getDesperfecto() != null && heladera.getDesperfecto()) {
            mensajeBuilder.append(" sufrió un desperfecto.");
        }
        return mensajeBuilder.toString();
    }
}