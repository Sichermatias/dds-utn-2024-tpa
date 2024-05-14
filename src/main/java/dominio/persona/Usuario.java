package dominio.persona;
import dominio.persona.verificadorContrasenias.Requisitos;
import dominio.persona.verificadorContrasenias.VerificadorContrasenia;
import lombok.Getter;

import java.util.ArrayList;
public class Usuario extends VerificadorContrasenia {

    private String nombreUsuario;
    @Getter
    private String contrasena;
    private Colaborador persona;

    public void crearUsuario(String contrasena, String usuario , ArrayList<Requisitos> requi){
        this.iniciarRequisitos(requi);
        this.nombreUsuario = usuario;
        if(validarContrasena(contrasena)){
            this.contrasena=contrasena;}
        } //else ingrese otra contrasena
}
