package main;

import dominio.services.broker.BrokerHandler;
import dominio.services.broker.BrokerReceptorMensajes;
import org.eclipse.paho.client.mqttv3.MqttClient;

public class Main {

    public static void main(String[] args) {
        BrokerHandler brokerHandler = new BrokerHandler();
        MqttClient cliente = brokerHandler.conectar();
        BrokerReceptorMensajes receptor = new BrokerReceptorMensajes();
        brokerHandler.suscribir(cliente, "dds2024/g12/heladeras/almagro/medrano", receptor);
    }
}

