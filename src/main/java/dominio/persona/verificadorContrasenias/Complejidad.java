package dominio.persona.verificadorContrasenias;

import dominio.archivos.LectorArchivo;

public class Complejidad extends Requisitos{
    public String ruta = "top10000_Peores_Contras.txt";
    public boolean evaluarContrasena(String contra) {
        String linea;
        LectorArchivo leer = new LectorArchivo();
        while((linea=leer.traerLinea(ruta))!=null){
            if(contra.equals(linea))return false;}
        return true;
    }

}
