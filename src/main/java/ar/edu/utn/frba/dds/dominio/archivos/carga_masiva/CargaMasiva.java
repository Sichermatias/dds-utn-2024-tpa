package ar.edu.utn.frba.dds.dominio.archivos.carga_masiva;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.archivos.LectorArchivo;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.PersonaHumanaRepositorio;

import java.util.Optional;

import static org.mockito.Mockito.mock;

public class CargaMasiva {
    private final LectorArchivo lectorArchivo = new LectorArchivo();
    public Mensajero mensajero;

    public CargaMasiva(Mensajero mensaje) {
    mensajero=mensaje;
    }

    public void cargarArchivo(String ruta, String separador) throws CampoInvalidoException {
        String linea;
        while ((linea = lectorArchivo.traerLinea(ruta)) != null) {
            String[] campos = SplitterLineas.split_linea(linea, separador);
            Colaborador colaborador =ProcesadorCampos.procesarCampos(campos);
            cargarPersona(colaborador);
        }
    }
    public void cargarPersona(Colaborador colaborador){
        PersonaHumanaRepositorio repositorio = PersonaHumanaRepositorio.obtenerInstancia();
        //BUSCO POR DNI SI YA TENGO A LA PERSONA
        Optional<Colaborador> personaGuardada = repositorio.buscarPorDNI(colaborador.getNroDocumento());
        personaGuardada.ifPresentOrElse(
                personaEncontrada -> repositorio.actualizar(colaborador),
                () -> {
                    Usuario usuario = new Usuario();
                    usuario.setNombreUsuario(colaborador.getNombre());
                    usuario.setContrasenia("contrabase");
                    Rol rol = new Rol();
                    rol.setNombreRol("COLABORADOR");
                    usuario.setRol(rol);
                    repositorio.agregar(colaborador);
                    String mail= colaborador.getMediosDeContacto().stream().filter(medioDeContacto -> "mail".equals(medioDeContacto.getNombreDeMedioDeContacto().getNombre())).findFirst().get().getValor();
                    Mensaje mensaje=new Mensaje(mail,"Usted fue procesado en el sistema como colaborador, por favor ingrese para validar sus datos");
                    mensajero.enviarMensaje(mensaje);
                     });
    }
}