package ar.edu.utn.frba.dds.dominio.archivos.carga_masiva;
import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.login.TipoRol;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.archivos.LectorArchivo;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.ArrayList;
import java.util.List;

public class CargaMasiva implements WithSimplePersistenceUnit {
    public LectorArchivo lectorArchivo = new LectorArchivo();
    public ColaboradorRepositorio repositorio= new ColaboradorRepositorio();
    public Mensajero mensajero;

    public CargaMasiva(Mensajero mensaje) {
        this.mensajero = mensaje;
    }

    public List<Colaboracion> cargarArchivo(String ruta, String separador) throws CampoInvalidoException {
        String linea;
        List<Colaboracion> colaboracionesTotales=new ArrayList<>();
        while ((linea = lectorArchivo.traerLinea(ruta)) != null) {
            String[] campos = SplitterLineas.split_linea(linea, separador);
            ProcesadorCampos procesadorCampos=new ProcesadorCampos();
            List<Colaboracion> colaboraciones = procesadorCampos.procesarCampos(campos);
            colaboracionesTotales.addAll(colaboraciones);
        }
        return colaboracionesTotales;
    }

    public void cargarPersona(Colaborador colaborador) {
        // BUSCO POR DNI SI YA TENGO A LA PERSONA
        List<Colaborador> personaGuardada = repositorio.buscarPorDNI(Colaborador.class, colaborador.getNroDocumento());

        if (!personaGuardada.isEmpty()) {
            repositorio.persistir(colaborador);
        } else {
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(colaborador.getNombre());
            usuario.setContrasenia("contrabase");

            Rol rol = new Rol();
            TipoRol tipoRol = TipoRol.COLABORADOR_HUMANO;
            rol.setTipo(tipoRol);
            rol.setNombreRol("COLABORADOR");
            usuario.setRol(rol);

            colaborador.setUsuario(usuario);
            repositorio.persistir(colaborador);

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
