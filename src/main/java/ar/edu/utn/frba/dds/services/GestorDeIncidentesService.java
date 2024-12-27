package ar.edu.utn.frba.dds.services;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.MailSender;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.TelegramSender;
import ar.edu.utn.frba.dds.dominio.utils.TextoPlanoConverter;
import ar.edu.utn.frba.dds.dominio.persona.Tecnico;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.services.messageSender.*;
import java.util.List;

import ar.edu.utn.frba.dds.models.repositories.imp.IncidenteRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.TecnicoRepositorio;
import lombok.Getter;
import lombok.Setter;
@Setter @Getter
public class GestorDeIncidentesService {

    private final IncidenteRepositorio incidentesRepositorio;
    private final TecnicoRepositorio tecnicoRepositorio;
    private final MailSender mailSender;

    public GestorDeIncidentesService(IncidenteRepositorio incidentesRepositorio, TecnicoRepositorio tecnicoRepositorio, MailSender mailSender) {
        this.incidentesRepositorio = incidentesRepositorio;
        this.tecnicoRepositorio = tecnicoRepositorio;
        this.mailSender = mailSender;
    }

    public void gestionarIncidentes() {
        List<Incidente> incidentes = incidentesRepositorio.buscarTodos(Incidente.class);
        List<Incidente> incidentesSinGestionar=incidentes.stream().filter(incidente -> incidente.getAsignado()==false).toList();
        for(Incidente incidente: incidentesSinGestionar)
            gestionarIncidente(incidente);
    }

    public void gestionarIncidente(Incidente incidenteAAsignar){

        var heladeraLocalidad = incidenteAAsignar.getHeladeraIncidente().getUbicacion().getLocalidad();
        boolean tecnicoDisponible = false;
        List<Tecnico> listaTecnicos = this.tecnicoRepositorio.buscarTodos(Tecnico.class);
        //Comparacion entre listado de Localidades del tecnico disponible y la localida de la heladera en incidente
        for(Tecnico tecnico : listaTecnicos){
            List<Localidad> listaDeLocalidades = tecnico.getLocalidadesDeServicio();
            for(Localidad unaLocalidadTecnico : listaDeLocalidades){
                if(heladeraLocalidad.equals(unaLocalidadTecnico)){
                    //Si matchea, le asigna el incidente al tecnico
                    tecnico.agregarIncidente(incidenteAAsignar);

                    //creo mensaje
                    String incidenteString = TextoPlanoConverter.convertirIncidenteAtextoPlano(incidenteAAsignar);
                    String textoAEnviar = "Hola " + tecnico.getNombre() + "!\n\nSe te ha asignado el incidente: " + incidenteAAsignar.getId() + "\n\nAqui tienes la informacion completa: " + incidenteString;

                    //crear estructura de mensaje
                    Mensaje mensajeATelegramTecnico = new Mensaje(tecnico.getTelegramID(),textoAEnviar);

                    //notificar tecnico
                    TelegramSender.enviarMensajeTelegram(mensajeATelegramTecnico);

                    Mensaje mensajeAMailTecnico = new Mensaje(tecnico.getMedioDeContacto("E-MAIL").getValor(), textoAEnviar);
                    this.mailSender.enviarMail(mensajeAMailTecnico);

                    tecnicoDisponible = true;
                    System.out.println("el tecnico asignado para el incidente:"+incidenteAAsignar + " es:" + tecnico.getNombre()+ " " + tecnico.getApellido());

                    this.incidentesRepositorio.actualizar(incidenteAAsignar);
                    this.tecnicoRepositorio.actualizar(tecnico);
                }
            }
        }
        //Si no encuentra ningun tecnico, tira el siguiente mensaje:
        if(!tecnicoDisponible){
            System.out.println("no hay tecnicos en el area solicitada para el incidente:"+incidenteAAsignar);
        }

    }
}
