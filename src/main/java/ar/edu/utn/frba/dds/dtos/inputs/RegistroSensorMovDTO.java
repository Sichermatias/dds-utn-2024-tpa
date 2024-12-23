package ar.edu.utn.frba.dds.dtos.inputs;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

@Getter @Setter
public class RegistroSensorMovDTO {
    private Long idHeladera;

    public RegistroSensorMovDTO(MqttMessage mqttMessage){
        JSONObject json = new JSONObject(mqttMessage.toString());
        this.idHeladera = json.getLong("idHeladera");
    }
}
