package ar.edu.utn.frba.dds.services;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.TelegramSender;
import ar.edu.utn.frba.dds.dominio.utils.TextoPlanoConverter;
import ar.edu.utn.frba.dds.dominio.persona.Tecnico;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.services.messageSender.*;
import java.util.List;

import ar.edu.utn.frba.dds.models.repositories.IIncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.imp.TecnicosRepository;
import lombok.Getter;
import lombok.Setter;
@Setter @Getter
public class GestorDeIncidentesService {

    private final IIncidentesRepository incidentesRepository;
    private final TecnicosRepository tecnicosRepository;

    public GestorDeIncidentesService(IIncidentesRepository incidentesRepository, TecnicosRepository tecnicosRepository) {
        this.incidentesRepository = incidentesRepository;
        this.tecnicosRepository = tecnicosRepository;
    }

    public void gestionarIncidentes() {
        List<Incidente> incidentesSinGestionar = incidentesRepository.buscarIncidentesSinAsignar();
        for(Incidente incidente: incidentesSinGestionar)
            gestionarIncidente(incidente);
    }

    public void gestionarIncidente(Incidente incidenteAAsignar){

        var heladeraLocalidad = incidenteAAsignar.getHeladeraIncidente().getUbicacion().getLocalidad();
        boolean tecnicoDisponible = false;
        List<Tecnico> listaTecnicos = this.tecnicosRepository.buscarTodos(Tecnico.class);
        //Comparacion entre listado de Localidades del tecnico disponible y la localida de la heladera en incidente
        for(Tecnico tecnico : listaTecnicos){
            List<Localidad> listaDeLocalidades = tecnico.getLocalidadesDeServicio();
            for(Localidad unaLocalidadTecnico : listaDeLocalidades){
                if(heladeraLocalidad.equals(unaLocalidadTecnico)){
                    //Si matchea, le asigna el incidente al tecnico
                    tecnico.agregarIncidente(incidenteAAsignar);

                    //creo mensaje
                    String incidenteString = TextoPlanoConverter.convertirAtextoPlano(incidenteAAsignar);
                    String textoAEnviar = "Hola " + tecnico.getNombre() + "!\n\nSe te ha asignado el incidente: " + incidenteAAsignar.getId() + "\n\nAqui tienes la informacion completa: " + incidenteString;

                    //crear estructura de mensaje
                    Mensaje mensajeTecnico = new Mensaje(tecnico.getTelegramID(),textoAEnviar);

                    //notificar tecnico
                    TelegramSender.enviarMensajeTelegram(mensajeTecnico);

                    tecnicoDisponible = true;
                    System.out.println("el tecnico asignado para el incidente:"+incidenteAAsignar + " es:" + tecnico.getNombre()+ " " + tecnico.getApellido());

                    this.incidentesRepository.actualizar(incidenteAAsignar);
                    this.tecnicosRepository.actualizar(tecnico);
                }
            }
        }
        //Si no encuentra ningun tecnico, tira el siguiente mensaje:
        if(!tecnicoDisponible){
            System.out.println("no hay tecnicos en el area solicitada para el incidente:"+incidenteAAsignar);
        }

    }
}
