package ar.edu.utn.frba.dds.dominio.services.broker;

import ar.edu.utn.frba.dds.dominio.utils.ConfigReader;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.Properties;

public class BrokerHandler {
    private final ConfigReader config;
    final String configPath = "brokerconfig.properties";

    public BrokerHandler(){
        config = new ConfigReader(configPath);
    }

    public MqttClient conectar(){
        Properties props;
        try {
            props = config.getProperties();
        } catch(Exception e) {
            System.out.println("Error al leer archivo de configuracion. Error: " + e);
            return null;
        }

        final String broker = props.getProperty("broker"); //same fromMail
        final String clientId = props.getProperty("clientId");

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            sampleClient.connect(connOpts);
            return sampleClient;
        } catch(MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
            return null;
        }
    }

    public void suscribir(MqttClient client, String topic, IMqttMessageListener receptor){

        try {
            client.subscribe(topic, receptor);
        } catch(Exception e) {
            System.out.println("Error al suscribirse al topic. Error: " + e);
        }

    }

    public void publicar(MqttClient cliente, String topico, String mensaje){

        MqttMessage message = this.crearMensaje(mensaje);

        try {
            cliente.publish(topico, message);
        } catch(MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public MqttMessage crearMensaje(String mensaje){
        return new MqttMessage(mensaje.getBytes());
    }
}
