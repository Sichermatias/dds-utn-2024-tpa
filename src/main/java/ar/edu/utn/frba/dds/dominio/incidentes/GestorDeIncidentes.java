package ar.edu.utn.frba.dds.dominio.incidentes;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.TelegramSender;
import ar.edu.utn.frba.dds.dominio.utils.TextoPlanoConverter;
import ar.edu.utn.frba.dds.dominio.persona.Tecnico;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.services.messageSender.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Setter @Getter
public class GestorDeIncidentes {

    private  List<Tecnico> listaTecnicos = new ArrayList<>();
    private static GestorDeIncidentes gestorDeIncidentes = null;

    public static GestorDeIncidentes getInstanciaGestor(){
        if(gestorDeIncidentes == null){
            gestorDeIncidentes = new GestorDeIncidentes();
        }
    return gestorDeIncidentes;
    }

    private void CargarEnListaDeTecnicos(Tecnico tecnico){
        this.listaTecnicos.add(tecnico);

    }

    public void gestionarIncidente(Incidente incidenteAAsignar){

        var heladeraLocalidad = incidenteAAsignar.getHeladeraIncidente().getUbicacion().getLocalidad();
        boolean tecnicoDisponible = false;
        //Comparacion entre listado de Localidades del tecnico disponible y la localida de la heladera en incidente
        for(Tecnico tecnico : listaTecnicos){
            List<Localidad> listaDeLocalidades = tecnico.getLocalidadesDeServicio();
            for(Localidad unaLocalidadTecnico : listaDeLocalidades){
                if(heladeraLocalidad == unaLocalidadTecnico){
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
                }
            }
        }
        //Si no encuentra ningun tecnico, tira el siguiente mensaje:
        if(!tecnicoDisponible){
            System.out.println("no hay tecnicos en el area solicitada para el incidente:"+incidenteAAsignar);
        }

    }
}
