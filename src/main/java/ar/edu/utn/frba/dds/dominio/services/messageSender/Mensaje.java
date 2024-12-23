package ar.edu.utn.frba.dds.dominio.services.messageSender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Mensaje {
    private String receptor;
    private String mensaje;
}
