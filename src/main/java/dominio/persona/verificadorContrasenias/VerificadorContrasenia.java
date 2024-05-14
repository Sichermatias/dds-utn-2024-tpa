package dominio.persona.verificadorContrasenias;

import java.util.ArrayList;

public class VerificadorContrasenia {
    public ArrayList<Requisitos> chequeos=new ArrayList<>();
    public void iniciarRequisitos(ArrayList<Requisitos> req){
        chequeos.addAll(req);
    }
    public boolean validarContrasena(String contrasena){
        return chequeos.stream().allMatch(requisitos -> requisitos.evaluarContrasena(contrasena));
    }
}
