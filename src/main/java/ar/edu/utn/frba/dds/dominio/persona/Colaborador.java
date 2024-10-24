package ar.edu.utn.frba.dds.dominio.persona;

import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.dominio.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.dominio.colaboracion.Frecuencia;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.services.messageSender.strategies.EstrategiaMensaje;
import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.formulario.FormularioRespondido;
import ar.edu.utn.frba.dds.dominio.infraestructura.FiltroSuscripcion;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.infraestructura.Suscripcion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "colaborador")
public class Colaborador extends Persistente {

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "apellido", columnDefinition = "VARCHAR(50)")
    private String apellido;

    @Column(name = "fechaNacimiento", columnDefinition = "DATE")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "nroDocumento", columnDefinition = "VARCHAR(100)")
    private String nroDocumento;

    @Column(name = "razonSocial", columnDefinition = "VARCHAR(100)")
    private String razonSocial;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "rubroPersonaJuridica", referencedColumnName = "id")
    private RubroPersonaJuridica rubroPersonaJuridica;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tipoPersonaJuridica", referencedColumnName = "id")
    private TipoPersonaJuridica tipoPersonaJuridica;

    @Enumerated(EnumType.STRING)
    private TipoPersona tipoPersona;

    @OneToOne
    @JoinColumn(name = "formularioRespondido_id", referencedColumnName = "id")
    private FormularioRespondido formularioRespondido;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private List<MedioDeContacto> mediosDeContacto=new ArrayList<>();

    @Embedded //TODO: está bien embeberlo?
    private Ubicacion ubicacion;

    @Column(name = "puntaje", columnDefinition = "INTEGER(9)")
    private Integer puntaje;

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Suscripcion> suscripciones = new ArrayList<>();

    @Column(name = "cantSemanalViandasDonadas", columnDefinition = "INTEGER(6)")
    private int cantSemanalViandasDonadas; // TODO 2024-07-03: cuando el colaborador dona una vianda, hay que sumarle al contador.

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private List<Tarjeta> tarjetas;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public Colaborador() {
        this.suscripciones = new ArrayList<>();
        this.tarjetas = new ArrayList<>();
    }
    public DonacionDinero donarDinero(Colaboracion colaboracion, Double monto, Frecuencia frecuencia){
        DonacionDinero donacionDinero=new DonacionDinero();
        donacionDinero.setColaboracion(colaboracion);
        donacionDinero.setMonto(monto);
        donacionDinero.setFrecuencia(frecuencia);
        donacionDinero.setFechaHoraAlta(LocalDateTime.now());
        donacionDinero.setActivo(true);
        return donacionDinero;
    }
    public Suscripcion suscribirseHeladera(Heladera heladera, FiltroSuscripcion filtro, Mensajero mensajero, String contacto) {
        Suscripcion suscripcion = new Suscripcion(this, heladera, filtro, mensajero, contacto);
        suscripciones.add(suscripcion);
        heladera.agregarSuscripcion(suscripcion);
        return suscripcion;
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

    public void modificarSuscripcion(Heladera heladera, FiltroSuscripcion filtro, EstrategiaMensaje estrategia, String contacto) {
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
    public boolean estaSuscrito(Heladera heladera){
        for (Suscripcion suscripcion:this.suscripciones){
            if (suscripcion.getColaborador()==this) return true;
        }
        return false;
    }
    public Suscripcion queSuscripcion(Heladera heladera){
        for (Suscripcion suscripcion:this.suscripciones){
            if (suscripcion.getColaborador()==this) return suscripcion;
        }
        return null;
    }
    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto){
        mediosDeContacto.add(medioDeContacto);
    }

    public String getNroDocumento() {
        return nroDocumento;
    }
}
