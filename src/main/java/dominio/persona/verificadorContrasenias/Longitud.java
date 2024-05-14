package dominio.persona.verificadorContrasenias;

public class Longitud extends Requisitos{
    private Integer longitudMinima;
    private Integer longitudMaxima;
    public boolean evaluarContrasena(String contra){
        if(contra.length() > longitudMinima && contra.length() < longitudMaxima){
            return true;
        }else{
            //mensaje de error
            return false;
        }
    }
}
