package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.PedidoDeApertura;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.PersonaVulnerable;
import ar.edu.utn.frba.dds.dtos.inputs.RegistroAperturaDTO;
import ar.edu.utn.frba.dds.dtos.inputs.RegistroSensorTempDTO;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.time.Duration;
import ar.edu.utn.frba.dds.dominio.utils.ConfigReader;
import java.time.LocalDateTime;
import java.util.Properties;

public class AperturaController  implements IMqttMessageListener {
    private final PedidoDeAperturaRepositorio pedidoDeAperturaRepositorio;
    private final ColaboradorRepositorio colaboradorRepositorio;
    private final PersonaVulnerableRepositorio personaVulnerableRepositorio;
    private final ConfigReader config;
    final String configPath = "aperturaHeladera.properties";

    public AperturaController(PedidoDeAperturaRepositorio pedidoDeAperturaRepositorio, ColaboradorRepositorio colaboradorRepositorio, PersonaVulnerableRepositorio personaVulnerableRepositorio){
        this.pedidoDeAperturaRepositorio = pedidoDeAperturaRepositorio;
        this.colaboradorRepositorio = colaboradorRepositorio;
        this.personaVulnerableRepositorio = personaVulnerableRepositorio;
        this.config = new ConfigReader(configPath);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        switch (topic) {
            case "dds2024/g12/heladeras/apertura" -> {
                RegistroAperturaDTO registroAperturaDTO = new RegistroAperturaDTO(mqttMessage);
                this.recibirPedidoApertura(registroAperturaDTO);
            }
            default -> throw new Exception(); //TODO: revisar excepcion de mensaje sensor
    }
}

    private void recibirPedidoApertura(RegistroAperturaDTO registroAperturaDTO) {
        Properties props;
        try {
            props = config.getProperties();
        } catch(Exception e) {
            System.out.println("Error al leer archivo de configuracion. Error: " + e);
            return;
        }

        PedidoDeApertura pedidoDeApertura = this.pedidoDeAperturaRepositorio.buscarPorHeladeraYTarjetaId(registroAperturaDTO.getIdHeladera(),registroAperturaDTO.getIdTarjeta());
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        int tiempoMaximoEspera = Integer.parseInt(props.getProperty("tiempoMaximoEspera"));
        long horasTranscurridas = Duration.between(pedidoDeApertura.getFechaHoraRealizada(), fechaHoraActual).toHours();
        Integer viandasActuales = pedidoDeApertura.getHeladera().getCantViandasActuales();

        if(horasTranscurridas >= tiempoMaximoEspera){
            System.out.println("El pedido ya no es valido. Por favor, vuelva a llenar el formulario");
            pedidoDeApertura.getHeladera().setCantViandasActuales(viandasActuales + pedidoDeApertura.getCantidadViandas());
        }
        else{
            if (pedidoDeApertura.getTarjeta().getPersonaVulnerable_id() != null){
                Long personaVulnerableId = pedidoDeApertura.getTarjeta().getPersonaVulnerable_id();
                PersonaVulnerable persona = personaVulnerableRepositorio.buscarPorIdVulnerable(personaVulnerableId);

                if (persona.getUsosDelDia() < persona.getCantUsosMaximosPorDia() && pedidoDeApertura.getHeladera().getCantViandasActuales() > 0){
                        //Le sumo un uso a la persona vulnerable
                        persona.setUsosDelDia(persona.getUsosDelDia() + 1);

                        //Le resto una vianda a la heladera
                        pedidoDeApertura.getHeladera().setCantViandasActuales(pedidoDeApertura.getHeladera().getCantViandasActuales() - 1);
                        System.out.println("Apertura realizada exitosamente");
                } else {
                    System.out.println("Se supero la cantidad de pedidos por dia o la heladera esta vacia");
                }

            } else {
                Integer colaboradorId = pedidoDeApertura.getTarjeta().getColabodador_id();
                System.out.println("Apertura realizada exitosamente");
            }
        }
        pedidoDeApertura.setValido(false);
        pedidoDeApertura.setFechaHoraBaja(fechaHoraActual);
    }
}
