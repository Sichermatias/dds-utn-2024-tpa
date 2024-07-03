package dominio.infraestructura;

public class EvaluadorTemperatura {
    public boolean hayAlerta(Double temperatura, Heladera heladera) {
        return temperatura > heladera.getModelo().getTempMinAceptable() &&
                temperatura < heladera.getModelo().getTempMaxAceptable();
    }
}
