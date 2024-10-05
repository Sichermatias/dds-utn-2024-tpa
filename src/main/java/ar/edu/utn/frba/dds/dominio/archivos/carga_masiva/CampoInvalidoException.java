package ar.edu.utn.frba.dds.dominio.archivos.carga_masiva;
public class CampoInvalidoException extends Exception {

    public CampoInvalidoException(String message) {
        super(message);
    }

    public CampoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
