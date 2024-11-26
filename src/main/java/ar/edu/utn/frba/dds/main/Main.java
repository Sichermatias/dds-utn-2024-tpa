package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.dominio.services.broker.BrokerHandler;
import ar.edu.utn.frba.dds.dominio.services.broker.BrokerReceptorMensajes;
import org.eclipse.paho.client.mqttv3.MqttClient;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        BrokerHandler brokerHandler = new BrokerHandler();
        MqttClient cliente = brokerHandler.conectar();
        BrokerReceptorMensajes receptor = new BrokerReceptorMensajes();

        brokerHandler.suscribir(cliente, "dds2024/g12/heladeras/temperatura", receptor);

        /*QuartzScheduler scheduler = new QuartzScheduler();
        scheduler.start();*/
    }
}

