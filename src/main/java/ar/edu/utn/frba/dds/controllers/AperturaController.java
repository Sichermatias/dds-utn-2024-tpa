package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.PedidoDeApertura;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dtos.inputs.RegistroAperturaDTO;
import ar.edu.utn.frba.dds.dtos.inputs.RegistroSensorTempDTO;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.HeladerasRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.PedidoDeAperturaRepositorio;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.Duration;
import java.time.LocalDateTime;

public class AperturaController  implements IMqttMessageListener {
    private final PedidoDeAperturaRepositorio pedidoDeAperturaRepositorio;
    private final ColaboradorRepositorio colaboradorRepositorio;

    public AperturaController(PedidoDeAperturaRepositorio pedidoDeAperturaRepositorio, ColaboradorRepositorio colaboradorRepositorio){
        this.pedidoDeAperturaRepositorio = pedidoDeAperturaRepositorio;
        this.colaboradorRepositorio = colaboradorRepositorio;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        switch (topic) {
            case "dds2024/g12/heladeras/apertura" -> {
                RegistroAperturaDTO registroAperturaDTO = new RegistroAperturaDTO(mqttMessage);
                this.recibirPedidoApertura(registroAperturaDTO);
                System.out.println("el registro es: " + registroAperturaDTO.getIdHeladera() + registroAperturaDTO.getIdTarjeta());
            }
            default -> throw new Exception(); //TODO: revisar excepcion de mensaje sensor
    }
}

    private void recibirPedidoApertura(RegistroAperturaDTO registroAperturaDTO) {
        PedidoDeApertura pedidoDeApertura = this.pedidoDeAperturaRepositorio.buscarPorHeladeraYTarjetaId(registroAperturaDTO.getIdHeladera(),registroAperturaDTO.getIdTarjeta());
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        int tiempoMaximoEspera = 3; //TODO: Moverlo a un config ya que debe ser un campo variable
        long horasTranscurridas = Duration.between(pedidoDeApertura.getFechaHoraRealizada(), fechaHoraActual).toHours();
        Integer viandasActuales = pedidoDeApertura.getHeladera().getCantViandasActuales();

        if(horasTranscurridas >= tiempoMaximoEspera){
            System.out.println("El pedido ya no es valido. Por favor, vuelva a llenar el formulario");
            pedidoDeApertura.getHeladera().setCantViandasActuales(viandasActuales + pedidoDeApertura.getCantidadViandas());

        }
        else{
            Integer colaboradorId = pedidoDeApertura.getTarjeta().getColabodador_id();
            System.out.println("Apertura realizada exitosamente");
        }
        pedidoDeApertura.setEsValido(false);
    }
    }
