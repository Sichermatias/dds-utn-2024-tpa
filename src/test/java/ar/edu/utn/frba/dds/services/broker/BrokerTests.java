package ar.edu.utn.frba.dds.services.broker;

import dominio.services.broker.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;


public class BrokerTests {

    @Test
    void conectarSuscribirPublicarEnBroker(){
        BrokerHandler brokerHandler = new BrokerHandler();
        MqttClient cliente = brokerHandler.conectar();
        BrokerReceptorMensajes receptor = new BrokerReceptorMensajes();
        brokerHandler.suscribir(cliente, "dds2024/g12/heladeras/almagro/medrano", receptor);
    }
}
