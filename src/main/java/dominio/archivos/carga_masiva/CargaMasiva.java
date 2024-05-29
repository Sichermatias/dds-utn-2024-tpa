package dominio.archivos.carga_masiva;
import dominio.archivos.LectorArchivo;

public class CargaMasiva {
    private final LectorArchivo lectorArchivo = new LectorArchivo();

    public void cargarArchivo(String ruta, String separador) throws CampoInvalidoException {
        String linea;
        while ((linea = lectorArchivo.traerLinea(ruta)) != null) {
            String[] campos = SplitterLineas.split_linea(linea, separador);
            ProcesadorCampos.procesarCampos(campos);
        }
    }
}