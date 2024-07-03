package dominio.incidentes;

import dominio.contacto.ubicacion.Localidad;
import dominio.infraestructura.Heladera;
import dominio.persona.Tecnico;

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

        var heladeraLocalidad = incidenteAAsignar.getHeladeraIncidente().getUbicacion().getLocalizacion();
        boolean tecnicoDisponible = false;
        //Comparacion entre listado de Localidades del tecnico disponible y la localida de la heladera en incidente
        for(Tecnico tecnico : listaTecnicos){
            List<Localidad> listaDeLocalidades = tecnico.getLocalidadesDeServicio();
            for(Localidad unaLocalidadTecnico : listaDeLocalidades){
                if(heladeraLocalidad == unaLocalidadTecnico){
                    //Si matchea, le asigna el incidente al tecnico
                    tecnico.agregarIncidente(incidenteAAsignar);
                    //TODO 2024-07-03: notificar a los tecnicos sobre incidente
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
