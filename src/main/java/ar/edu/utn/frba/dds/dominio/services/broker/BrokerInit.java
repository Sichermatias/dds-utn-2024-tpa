package ar.edu.utn.frba.dds.dominio.services.broker;

import ar.edu.utn.frba.dds.controllers.FactoryController;
import ar.edu.utn.frba.dds.controllers.SensoresController;
import org.eclipse.paho.client.mqttv3.MqttClient;

public class BrokerInit {
    public BrokerInit() {
    }
    public static void init() {
        BrokerHandler brokerHandler = new BrokerHandler();
        MqttClient cliente = brokerHandler.conectar();
        SensoresController sensoresController = (SensoresController) FactoryController.controller("Sensores");

        brokerHandler.suscribir(cliente, "dds2024/g12/heladeras/temperatura", sensoresController);
        brokerHandler.suscribir(cliente, "dds2024/g12/heladeras/movimiento", sensoresController);
    }
}
