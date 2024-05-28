package dominio.services.messageSender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Mensaje {
    private String receptor;
    private String mensaje;
}
