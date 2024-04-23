package dominio.persona;
import dominio.persona.verificadorContrasenias.Requisitos;
import lombok.Getter;

import java.util.ArrayList;
public class Usuario{

    private String nombreUsuario;
    @Getter
    private String contrasena;
    public ArrayList<Requisitos> chequeos=new ArrayList<>();
    private Colaborador persona;



    private void iniciarRequisitos(ArrayList<Requisitos> req){
        chequeos.addAll(req);
    }
    public void crearUsuario(String contrasena, String usuario , ArrayList<Requisitos> requi){
        this.iniciarRequisitos(requi);
        this.nombreUsuario = usuario;
        if(chequeos.stream().allMatch(requisitos-> requisitos.evaluarContrasena(contrasena))){
            this.contrasena=contrasena;
        }
    }


}