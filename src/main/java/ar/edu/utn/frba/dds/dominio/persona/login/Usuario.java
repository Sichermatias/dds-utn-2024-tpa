package ar.edu.utn.frba.dds.dominio.persona.login;
import ar.edu.utn.frba.dds.dominio.persona.verificadorContrasenias.Requisitos;
import ar.edu.utn.frba.dds.dominio.persona.verificadorContrasenias.VerificadorContrasenia;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "Usuario")
@Data
public class Usuario extends VerificadorContrasenia {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombreUsuario", columnDefinition = "VARCHAR(50)", unique = true)
    private String nombreUsuario;

    @Column(name = "contraseñaUsuario", columnDefinition = "VARCHAR(50)")
    private String contrasenia;
    //private Colaborador colaborador;
    @ManyToOne
    @JoinTable(name = "usuario_Rol",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private Rol rol;

    public void crearUsuario(String contrasenia, String usuario , ArrayList<Requisitos> requi){
        this.iniciarRequisitos(requi);
        this.nombreUsuario = usuario;
        if(validarContrasena(contrasenia)){
            this.contrasenia =contrasenia;
        } //else ingrese otra contrasenia
    }

    public boolean tienePermiso(String nombreInternoPermiso) {
        return this.rol.tienePermiso(nombreInternoPermiso);
    }
}
