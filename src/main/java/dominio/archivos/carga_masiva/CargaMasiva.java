package dominio.archivos.carga_masiva;
import dominio.archivos.LectorArchivo;
import dominio.contacto.MedioDeContacto;
import dominio.persona.Persona;
import dominio.persona.login.Rol;
import dominio.persona.login.Usuario;
import dominio.repositories.PersonaHumanaRepositorio;

import java.util.ArrayList;
import java.util.Optional;

public class CargaMasiva {
    private final LectorArchivo lectorArchivo = new LectorArchivo();

    public void cargarArchivo(String ruta, String separador) throws CampoInvalidoException {
        String linea;
        while ((linea = lectorArchivo.traerLinea(ruta)) != null) {
            String[] campos = SplitterLineas.split_linea(linea, separador);
            Persona persona=ProcesadorCampos.procesarCampos(campos);
            cargarPersona(persona);
        }
    }
    public void cargarPersona(Persona persona){
        PersonaHumanaRepositorio repositorio = PersonaHumanaRepositorio.obtenerInstancia();
        //BUSCO POR DNI SI YA TENGO A LA PERSONA
        Optional<Persona> personaGuardada = repositorio.buscarPorDNI(persona.getNroDocumento());
        personaGuardada.ifPresentOrElse(
                personaEncontrada -> {
                    personaEncontrada.agregarColaboraciones(persona.getColaboraciones());
                    personaEncontrada.agregarMediosDeContacto((ArrayList<MedioDeContacto>) persona.getMediosDeContacto());
                    repositorio.actualizar(persona);
                },
                () -> {
                    Usuario usuario=new Usuario();
                    usuario.setNombreUsuario(persona.getNombre());
                    usuario.setContrasena("contrabase");
                    Rol rol=new Rol();
                    rol.setNombreRol("COLABORADOR");
                    usuario.setRol(rol);
                    repositorio.agregar(persona); //LO AGREGO
                    //Mensaje mensaje=new Mensaje(email,"Usted fue procesado en el sistema como colaborador, por favor ingrese para validar sus datos");
                    //new EstrategiaMail().enviarMensaje(mensaje);
                });
    }
}