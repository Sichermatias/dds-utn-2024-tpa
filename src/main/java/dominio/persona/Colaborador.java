package dominio.persona;

import dominio.Persistente;
import dominio.contacto.MedioDeContacto;
import dominio.contacto.ubicacion.Ubicacion;
import dominio.formulario.FormularioRespondido;
import dominio.infraestructura.FiltroSuscripcion;
import dominio.infraestructura.Heladera;
import dominio.infraestructura.Suscripcion;
import dominio.services.messageSender.Mensajero;
import dominio.services.messageSender.strategies.EstrategiaMensaje;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colaborador")
@Setter
@Getter
public class Colaborador extends Persistente {

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "apellido", columnDefinition = "VARCHAR(50)")
    private String apellido;

    @Column(name = "fechaNacimiento", columnDefinition = "DATE")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "nroDocumento", columnDefinition = "INTEGER(11)")
    private Integer nroDocumento;

    @Column(name = "razonSocial", columnDefinition = "VARCHAR(100)")
    private String razonSocial;

    @ManyToOne
    @JoinColumn(name = "rubroPersonaJuridica")
    private RubroPersonaJuridica rubroPersonaJuridica;

    @ManyToOne
    @JoinColumn(name = "tipoPersonaJuridica")
    private TipoPersonaJuridica tipoPersonaJuridica;

    @Enumerated(EnumType.STRING)
    private TipoPersona tipoPersona;

    @OneToOne
    @JoinColumn(name = "formularioRespondido_id")
    private FormularioRespondido formularioRespondido;

    @OneToMany
    @JoinColumn(name = "colaborador_id")
    private List<MedioDeContacto> mediosDeContacto=new ArrayList<>();

    @Embedded //TODO: está bien embeberlo?
    private Ubicacion ubicacion;

    @Column(name = "puntaje", columnDefinition = "INTEGER(9)")
    private Integer puntaje;

    //TODO: preguntar si es necesario la bidireccionalidad
    @OneToMany
    private List<Suscripcion>suscripciones;

    @Column(name = "cantSemanalViandasDonadas", columnDefinition = "INTEGER(6)")
    private int cantSemanalViandasDonadas; // TODO 2024-07-03: cuando el colaborador dona una vianda, hay que sumarle al contador.

    @OneToMany
    @JoinColumn(name = "colaborador_id")
    private List<Tarjeta> tarjetas;

    public Colaborador() {
        this.suscripciones = new ArrayList<>();
        this.tarjetas = new ArrayList<>();
    }

    public void suscribirseHeladera(Heladera heladera, FiltroSuscripcion filtro, Mensajero mensajero, String contacto) {
        Suscripcion suscripcion = new Suscripcion(this, heladera, filtro, mensajero, contacto);
        suscripciones.add(suscripcion);
        heladera.agregarSuscripcion(suscripcion);
    }

    public void cancelarSuscripcion(Heladera heladera) {
        Suscripcion suscripcionAEliminar = null;
        for (Suscripcion suscripcion : suscripciones) {
            if (suscripcion.getHeladera().equals(heladera)) {
                suscripcionAEliminar = suscripcion;
                break;
            }
        }
        if (suscripcionAEliminar != null) {
            suscripciones.remove(suscripcionAEliminar);
            heladera.eliminarSuscripcion(suscripcionAEliminar);
        }
    }

    public void modificarSuscripcion(Heladera heladera,FiltroSuscripcion filtro, EstrategiaMensaje estrategia, String contacto) {
        for (Suscripcion suscripcion : suscripciones) {
            if (suscripcion.getHeladera().equals(heladera)) {
                suscripcion.setFiltro(filtro);
                suscripcion.getMensajero().cambiarEstrategia(estrategia);
                suscripcion.setContacto(contacto);
                break;
            }
        }
    }
    public void agregarMediosDeContacto(ArrayList<MedioDeContacto> mediosDeContacto) {
        this.mediosDeContacto.addAll(mediosDeContacto);
    }
    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto){
        mediosDeContacto.add(medioDeContacto);
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }
}
