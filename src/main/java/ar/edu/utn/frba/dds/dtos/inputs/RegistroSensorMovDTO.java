package ar.edu.utn.frba.dds.dtos.inputs;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

@Getter @Setter
public class RegistroSensorMovDTO {
    private String idHeladera;

    RegistroSensorMovDTO(MqttMessage mqttMessage){
        JSONObject json = new JSONObject(mqttMessage.toString());
        this.idHeladera = json.getString("idHeladera");
    }
}
