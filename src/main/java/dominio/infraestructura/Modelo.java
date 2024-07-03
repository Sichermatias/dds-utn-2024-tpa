package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Modelo {
    private String nombre;
    private Double tempMinAceptable;
    private Double tempMaxAceptable;
}
