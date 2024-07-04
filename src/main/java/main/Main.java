package main;

import dominio.services.broker.BrokerHandler;
import dominio.services.broker.BrokerReceptorMensajes;
import dominio.services.cronjobs.QuartzScheduler;
import dominio.services.cronjobs.QuartzSchedulerSemanal;
import org.eclipse.paho.client.mqttv3.MqttClient;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        BrokerHandler brokerHandler = new BrokerHandler();
        MqttClient cliente = brokerHandler.conectar();
        BrokerReceptorMensajes receptor = new BrokerReceptorMensajes();

        brokerHandler.suscribir(cliente, "dds2024/g12/heladeras/temperatura", receptor);

        brokerHandler.publicar(cliente, "dds2024/g12/heladeras/temperatura", "{idHeladera:1, temperatura:20.0}");

        /*QuartzScheduler scheduler = new QuartzScheduler();
        scheduler.start();*/

        QuartzSchedulerSemanal schedulerSemanal = new QuartzSchedulerSemanal();
        schedulerSemanal.start();
    }
}

