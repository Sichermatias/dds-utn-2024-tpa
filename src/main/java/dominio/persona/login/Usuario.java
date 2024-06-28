package dominio.persona.login;
import dominio.persona.verificadorContrasenias.Requisitos;
import dominio.persona.verificadorContrasenias.VerificadorContrasenia;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Usuario extends VerificadorContrasenia {

    private String nombreUsuario;
    private String contrasena;
    //private Colaborador colaborador;
    private Rol rol;

    public void crearUsuario(String contrasena, String usuario , ArrayList<Requisitos> requi){
        this.iniciarRequisitos(requi);
        this.nombreUsuario = usuario;
        if(validarContrasena(contrasena)){
            this.contrasena=contrasena;}
        } //else ingrese otra contrasena
}
