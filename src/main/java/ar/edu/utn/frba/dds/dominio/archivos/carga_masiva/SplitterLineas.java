package ar.edu.utn.frba.dds.dominio.archivos.carga_masiva;

public class SplitterLineas {
    public static String[] split_linea(String linea, String separador){
        return linea.split(separador);
    }
}
