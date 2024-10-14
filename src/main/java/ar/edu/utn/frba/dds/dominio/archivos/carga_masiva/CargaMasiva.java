package ar.edu.utn.frba.dds.dominio.archivos.carga_masiva;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.archivos.LectorArchivo;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;

import java.util.List;
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
    public void cargarPersona(Colaborador colaborador) {
        ColaboradorRepositorio repositorio = ColaboradorRepositorio.getInstancia();

        // BUSCO POR DNI SI YA TENGO A LA PERSONA
        List<Colaborador> personaGuardada = repositorio.buscarPorDNI(Colaborador.class, String.valueOf(colaborador.getNroDocumento()));

        if (!personaGuardada.isEmpty()) {
            // Si la persona ya existe, la actualizo
            repositorio.actualizar(colaborador);
        } else {
            // Si la persona no existe, la creo
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(colaborador.getNombre());
            usuario.setContrasenia("contrabase");  // Puedes usar una lógica de generación de contraseñas más robusta aquí

            Rol rol = new Rol();
            rol.setNombreRol("COLABORADOR");
            usuario.setRol(rol);

            repositorio.persistir(colaborador);

            // Enviar mensaje al contacto por mail (si está disponible)
            colaborador.getMediosDeContacto().stream()
                    .filter(medioDeContacto -> "mail".equals(medioDeContacto.getNombreDeMedioDeContacto().getNombre()))
                    .findFirst()
                    .ifPresent(medioDeContacto -> {
                        String mail = medioDeContacto.getValor();
                        Mensaje mensaje = new Mensaje(mail, "Usted fue procesado en el sistema como colaborador, por favor ingrese para validar sus datos");
                        mensajero.enviarMensaje(mensaje);
                    });
        }
    }
}