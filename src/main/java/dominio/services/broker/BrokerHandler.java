package dominio.services.broker;

import dominio.utils.ConfigReader;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.Properties;
import static java.lang.Thread.sleep;

public class BrokerHandler {
    private ConfigReader config;
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

    public boolean suscribir(MqttClient client, String topic, BrokerReceptorMensajes receptor){

        try {
            client.subscribe(topic, receptor);
            sleep(20000);
            return true;
        } catch(Exception e) {
            System.out.println("Error al suscribirse al topic. Error: " + e);
            return false;
        }

    }

    public boolean publicar(MqttClient cliente, String topico, String mensaje){

        MqttMessage message = this.crearMensaje(mensaje);

        try {
            cliente.publish(topico, message);
            return true;
        } catch(MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
            return false;
        }
    }

    public MqttMessage crearMensaje(String mensaje){
        return new MqttMessage(mensaje.getBytes());
    }
}
