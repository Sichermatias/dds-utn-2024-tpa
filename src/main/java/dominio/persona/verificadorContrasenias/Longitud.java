package dominio.persona.verificadorContrasenias;

public class Longitud extends Requisitos{
    public boolean evaluarContrasena(String contra){
        if(contra.length() > 7 && contra.length() < 64){
            return true;
        }else{
            return false;
        }
    }
}
