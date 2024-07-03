package dominio.persona;

import dominio.colaboracion.Colaboracion;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Colaborador {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private TipoDocumento tipoDocumento;
    private Integer nroDocumento;
    private String razonSocial;
    private RubroPersonaJuridica rubroPersonaJuridica;
    private TipoPersonaJuridica tipoPersonaJuridica;
    private TipoPersona tipoPersona;
    private FormularioRespondido formularioRespondido;
    private List<MedioDeContacto> mediosDeContacto=new ArrayList<>();
    private Ubicacion ubicacion;
    private ArrayList<Colaboracion> colaboraciones=new ArrayList<>();
    private CalculadorDePuntosAcumulados calculadorDePuntos;
    private List<Suscripcion>suscripciones;
    private int cantSemanalViandasDonadas; // TODO 2024-07-03: cuando el colaborador dona una vianda, hay que sumarle al contador.

    public Colaborador() {
        this.suscripciones = new ArrayList<>();
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
        mediosDeContacto.addAll(mediosDeContacto);
    }
    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto){
        mediosDeContacto.add(medioDeContacto);
    }
    public void agregarColaboraciones(ArrayList<Colaboracion> colaboracion) {
        colaboraciones.addAll(colaboracion);
    }

    public void agregarColaboracion(Colaboracion colaboracion) {
        colaboraciones.add(colaboracion);
    }
    public Integer getNroDocumento() {
        return nroDocumento;
    }
}
