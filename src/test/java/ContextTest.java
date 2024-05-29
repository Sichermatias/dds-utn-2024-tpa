import dominio.archivos.carga_masiva.CampoInvalidoException;
import dominio.archivos.carga_masiva.CargaMasiva;
import dominio.colaboracion.TipoColaboracion;
import dominio.colaboracion.TipoColaboracionRegistry;
import dominio.persona.login.Usuario;
import dominio.persona.verificadorContrasenias.Complejidad;
import dominio.persona.verificadorContrasenias.Longitud;
import dominio.persona.verificadorContrasenias.Requisitos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class ContextTest {
    //ACA INICIALIZAMOS
    Longitud tam = new Longitud();
    Complejidad top = new Complejidad();
    ArrayList<Requisitos> chequeos=new ArrayList<>();
    //ACA LOS TEST
    CargaMasiva cargaOk=new CargaMasiva();
    @Test void cargaMasivaOk() throws CampoInvalidoException {
        String rutaBase = System.getProperty("user.dir"); // Obtiene el directorio base del proyecto
        String rutaArchivo = rutaBase + File.separator+ "CargaMasivaTest.csv"; // Construye la ruta relativa al archivo
        cargaOk.cargarArchivo(rutaArchivo,";");
    }
    @Test
    public void noSeCreaPorTamanioContraseña() {
        Usuario usuario = new Usuario();

        chequeos.add(tam);
        usuario.crearUsuario("123", "G19", chequeos);
        Assertions.assertNotEquals("123", usuario.getContrasena());
    }

    @Test
    public void seCreaContraseña() {
        Usuario usuario = new Usuario();
        chequeos.add(tam);
        chequeos.add(top);
        usuario.crearUsuario("djkasbjkdbsakkdkasb", "G19", chequeos);
        Assertions.assertEquals("djkasbjkdbsakkdkasb", usuario.getContrasena());
    }

    @Test
    public void noSeCreaPortop10000Contraseña() {
        Usuario usuario = new Usuario();
        chequeos.add(top);
        usuario.crearUsuario("123456789", "G19", chequeos);
        Assertions.assertNotEquals("123456789", usuario.getContrasena());
    }
}
