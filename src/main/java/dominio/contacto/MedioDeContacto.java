package dominio.contacto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MedioDeContacto {
    private NombreDeMedioDeContacto nombreDeMedioDeContacto;
    private String valor;
}
