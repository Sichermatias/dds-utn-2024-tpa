package dominio.archivos.carga_masiva;

import dominio.colaboracion.TipoColaboracionRegistry;
import dominio.contacto.MedioDeContacto;
import dominio.contacto.NombreDeMedioDeContacto;
import dominio.persona.Persona;
import dominio.persona.TipoDocumento;
import dominio.colaboracion.Colaboracion;
import dominio.colaboracion.TipoColaboracion;
import dominio.persona.TipoPersona;
import dominio.persona.login.Rol;
import dominio.persona.login.Usuario;
import dominio.repositories.PersonaHumanaRepositorio;
import dominio.services.messageSender.Mensaje;
import dominio.services.messageSender.strategies.EstrategiaMail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class ProcesadorCampos {

    public static Persona procesarCampos(String[] campos) throws CampoInvalidoException {
        String tipoDocumento = campos[0];
        String documento = campos[1];
        String nombre = campos[2];
        String apellido = campos[3];
        String email = campos[4];
        String fechaColaboracion = campos[5];
        String formaColaboracion = campos[6];
        String cantidadColaboraciones = campos[7];

        Persona persona = new Persona();
        persona.setTipoPersona(TipoPersona.HUMANA);

        if (ValidadorCampos.validarTipoDocumento(tipoDocumento)) {
            persona.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
        } else {
            throw new CampoInvalidoException("Tipo de documento inválido: " + tipoDocumento);
        }

        if (ValidadorCampos.validarDocumento(documento)) {
            persona.setNroDocumento(Integer.parseInt(documento));
        } else {
            throw new CampoInvalidoException("Documento inválido: " + documento);
        }

        if (ValidadorCampos.validarNombre(nombre)) {
            persona.setNombre(nombre);
        } else {
            throw new CampoInvalidoException("Nombre inválido: " + nombre);
        }

        if (ValidadorCampos.validarNombre(apellido)) {
            persona.setApellido(apellido);
        } else {
            throw new CampoInvalidoException("Apellido inválido: " + apellido);
        }

        if (ValidadorCampos.validarEmail(email)) {
            NombreDeMedioDeContacto contacto = new NombreDeMedioDeContacto();
            contacto.setNombre("mail");
            MedioDeContacto mail = new MedioDeContacto();
            mail.setNombreDeMedioDeContacto(contacto);
            mail.setValor(email);
            persona.agregarMedioDeContacto(mail);
        } else {
            throw new CampoInvalidoException("Email inválido: " + email);
        }


        procesarColaboraciones(persona, fechaColaboracion, formaColaboracion, cantidadColaboraciones);

        return persona;
    }

    public static void procesarColaboraciones(Persona colaborador, String fecha, String forma, String cantidadStr) throws CampoInvalidoException {
        if (!ValidadorCampos.validarCantidad(cantidadStr)) {
            throw new CampoInvalidoException("Cantidad de colaboraciones inválida: " + cantidadStr);
        }

        int cantidad = Integer.parseInt(cantidadStr);
        for (int i = 0; i < cantidad; i++) {
            Colaboracion colaboracion = new Colaboracion();
            TipoColaboracion tipo = TipoColaboracionRegistry.create(forma);
            if (ValidadorCampos.validarFormaColaboracion(forma)) {
                tipo.setNombreTipo(forma);
            } else {
                throw new CampoInvalidoException("Forma de colaboración inválida: " + forma);
            }
            if (ValidadorCampos.validarFecha(fecha)) {
                colaboracion.setFechaColaboracion(fecha);
            } else {
                throw new CampoInvalidoException("Fecha de colaboración inválida: " + fecha);
            }
            colaboracion.cambiarTipoColaboracion(tipo);
            colaborador.agregarColaboracion(colaboracion);
        }
    }
}