package dominio.services.broker;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static java.lang.Thread.sleep;

public class BrokerSuscriptor {
    public boolean suscribir(MqttClient client, String topic, BrokerReceptor receptor){

        try {
            client.subscribe(topic, receptor);
            sleep(20000);
            return true;
        } catch(Exception e) {
            System.out.println("Error al suscribirse al topic. Error: " + e);
            return false;
        }

    }
}
