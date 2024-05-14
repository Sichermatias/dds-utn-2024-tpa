package dominio.archivos.carga_masiva;
import dominio.archivos.LectorArchivo;

public class CargaMasiva{
    public void cargarArchivo(String ruta, String separador){
        String linea=LectorArchivo.traerLinea(ruta);
        String[] campos=SplitterLineas.split_linea(linea,separador);
        ProcesadorCampos.procesarCampos(campos);
    }
}
