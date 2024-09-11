package dominio.infraestructura;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FiltroSuscripcion {
    Integer viandasParaLlenar=null;
    Integer minViandas=null;
    Boolean desperfecto=null;

    public FiltroSuscripcion(Integer viandasParaLlenar, Integer minViandas, Boolean desperfecto) {
        this.viandasParaLlenar = viandasParaLlenar;
        this.minViandas = minViandas;
        this.desperfecto = desperfecto;
    }
    public boolean cumpleCondiciones(Heladera heladera) {
        if (viandasParaLlenar != null && heladera.getViandasParaLlenar() <= viandasParaLlenar) {
            return true;
        }
        if (minViandas != null && heladera.getCantViandasActuales() <= minViandas) {
            return true;
        }
        if (desperfecto != null && heladera.getDesperfecto()) {
            return true;
        }
        return false;
    }
}
