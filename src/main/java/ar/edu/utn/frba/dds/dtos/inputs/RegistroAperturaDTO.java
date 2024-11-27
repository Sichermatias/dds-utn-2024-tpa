package ar.edu.utn.frba.dds.dtos.inputs;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

@Getter @Setter
public class RegistroAperturaDTO {
    private int idHeladera;
    private Long idTarjeta;

    public RegistroAperturaDTO(MqttMessage mqttMessage){
        JSONObject json = new JSONObject(mqttMessage.toString());
        this.idHeladera = json.getInt("idHeladera");
        this.idTarjeta = json.getLong("idTarjeta");
    }
}
