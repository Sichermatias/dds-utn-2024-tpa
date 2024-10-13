import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.dominio.persona.verificadorContrasenias.Complejidad;
import ar.edu.utn.frba.dds.dominio.persona.verificadorContrasenias.Longitud;
import ar.edu.utn.frba.dds.dominio.persona.verificadorContrasenias.Requisitos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ContextTest {
    //ACA INICIALIZAMOS
    Longitud tam = new Longitud();
    Complejidad top = new Complejidad();
    ArrayList<Requisitos> chequeos=new ArrayList<>();
    //ACA LOS TEST
    @Test
    public void noSeCreaPorTamanioContrasenia() {
        Usuario usuario = new Usuario();

        chequeos.add(tam);
        usuario.crearUsuario("123", "G19", chequeos);
        Assertions.assertNotEquals("123", usuario.getContrasenia());
    }

    @Test
    public void seCreaContrasenia() {
        Usuario usuario = new Usuario();
        chequeos.add(tam);
        chequeos.add(top);
        usuario.crearUsuario("djkasbjkdbsakkdkasb", "G19", chequeos);
        Assertions.assertEquals("djkasbjkdbsakkdkasb", usuario.getContrasenia());
    }

    @Test
    public void noSeCreaPortop10000Contrasenia() {
        Usuario usuario = new Usuario();
        chequeos.add(top);
        usuario.crearUsuario("123456789", "G19", chequeos);
        Assertions.assertNotEquals("123456789", usuario.getContrasenia());
    }
}
