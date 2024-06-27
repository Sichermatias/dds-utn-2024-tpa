package ar.edu.utn.frba.dds.services.broker;

import dominio.services.broker.BrokerConector;
import dominio.services.broker.BrokerPublicador;
import dominio.services.broker.BrokerReceptor;
import dominio.services.broker.BrokerSuscriptor;
import dominio.services.messageSender.Mensaje;
import dominio.services.messageSender.adapters.MailSender;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;


public class BrokerTests {

    @Test
    void conectarSuscribirPublicarEnBroker(){
        BrokerConector brokerConector = new BrokerConector();
        MqttClient cliente = brokerConector.conectar();
        BrokerSuscriptor brokerSuscriptor = new BrokerSuscriptor();
        BrokerReceptor brokerReceptor = new BrokerReceptor();
        brokerSuscriptor.suscribir(cliente, "dds2024/heladeras/almagro/medrano", brokerReceptor);

        BrokerPublicador brokerPublicador = new BrokerPublicador();
        MqttMessage mensaje = new MqttMessage("Mensaje de prueba".getBytes());

        brokerPublicador.publicar(cliente, "dds2024/heladeras/almagro/medrano", mensaje);
    }
}
