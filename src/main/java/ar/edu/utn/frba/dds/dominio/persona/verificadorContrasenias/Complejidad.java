package ar.edu.utn.frba.dds.dominio.persona.verificadorContrasenias;

import ar.edu.utn.frba.dds.dominio.archivos.LectorArchivo;

public class Complejidad extends Requisitos{
    public String ruta = "/top10000_Peores_Contras.txt";
    public boolean evaluarContrasena(String contra) {
        String linea;
        LectorArchivo leer = new LectorArchivo();
        while((linea=leer.traerLinea(ruta))!=null){
            if(contra.equals(linea))
                //mensaje error
                return false;}
        return true;
    }

}
