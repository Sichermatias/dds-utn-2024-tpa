package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.Tarjeta;
import ar.edu.utn.frba.dds.dominio.persona.TipoDocumento;
import ar.edu.utn.frba.dds.dominio.persona.TipoPersona;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.TipoRol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.security.Roles;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Initializer implements WithSimplePersistenceUnit {

    public static void init() {
        precargarDatos();
    }

    private static void precargarDatos() {
        ColaboracionRepositorio repositorio=ColaboracionRepositorio.getInstancia();
        List<Rol> roles = repositorio.buscarTodos(Rol.class);
        if(roles.size()<3) {
            Rol admin = new Rol();
            admin.setTipo(TipoRol.ADMIN);
            admin.setNombreRol("Administrador");

            repositorio.persistir(admin);

            Rol humana = new Rol();
            humana.setTipo(TipoRol.COLABORADOR_HUMANO);
            humana.setNombreRol("PersonaHumana");

            repositorio.persistir(humana);

            Rol juridica = new Rol();
            juridica.setTipo(TipoRol.COLABORADOR_JURIDICO);
            juridica.setNombreRol("PersonaJuridica");

            repositorio.persistir(juridica);

            Rol tecnico = new Rol();
            tecnico.setTipo(TipoRol.TECNICO);
            tecnico.setNombreRol("Tecnico");

            repositorio.persistir(tecnico);
        }

        //USUARIO CREDENCIALES ADMIN
        String nombreUsuario = "admin";
        String password = "admin";

        List<Usuario> usuarioexistente = repositorio.buscarPorNombre(Usuario.class,nombreUsuario);
        if(usuarioexistente.isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setContrasenia(password);
            usuario.setNombreUsuario(nombreUsuario);
            List<Rol> rol = repositorio.buscarPorRol(Rol.class,"Administrador");
            usuario.setRol(rol.get(0));
            repositorio.persistir(usuario);
        }
        //MODELOS HELADERA

        //UBICACIONES PARA PUNTOS RECOMENDADOS

        //PREMIOS PRECARGADOS (PODEMOS CARGARLOS TODOS CON UN COLABORADOR JURÍDICO IGUAL
    }
}
