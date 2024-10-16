package ar.edu.utn.frba.dds.dominio.archivos.carga_masiva;

import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.TipoDocumento;
import ar.edu.utn.frba.dds.dominio.persona.TipoPersona;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class ProcesadorCampos implements WithSimplePersistenceUnit {

    public static ColaboracionRepositorio repositorio= new ColaboracionRepositorio();

    public static Colaborador procesarCampos(String[] campos) throws CampoInvalidoException {
        Colaborador colaborador=new Colaborador();
        String tipoDocumento = campos[0];
        String documento = campos[1];
        String nombre = campos[2];
        String apellido = campos[3];
        String email = campos[4];
        String fechaColaboracion = campos[5];
        String formaColaboracion = campos[6];
        String cantidadColaboraciones = campos[7];

        colaborador.setTipoPersona(TipoPersona.HUMANA);

        if (ValidadorCampos.validarTipoDocumento(tipoDocumento)) {
            colaborador.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
        } else {
            throw new CampoInvalidoException("Tipo de documento inválido: " + tipoDocumento);
        }

        if (ValidadorCampos.validarDocumento(documento)) {
            colaborador.setNroDocumento(documento);
        } else {
            throw new CampoInvalidoException("Documento inválido: " + documento);
        }

        if (ValidadorCampos.validarNombre(nombre)) {
            colaborador.setNombre(nombre);
        } else {
            throw new CampoInvalidoException("Nombre inválido: " + nombre);
        }

        if (ValidadorCampos.validarNombre(apellido)) {
            colaborador.setApellido(apellido);
        } else {
            throw new CampoInvalidoException("Apellido inválido: " + apellido);
        }

        if (ValidadorCampos.validarEmail(email)) {
            NombreDeMedioDeContacto contacto = new NombreDeMedioDeContacto("mail");
            MedioDeContacto mail = new MedioDeContacto(contacto,email);
            colaborador.agregarMedioDeContacto(mail);
        } else {
            throw new CampoInvalidoException("Email inválido: " + email);
        }

        procesarColaboraciones(colaborador, fechaColaboracion, formaColaboracion, cantidadColaboraciones);
        return colaborador;
    }

    public static void procesarColaboraciones(Colaborador colaborador, String fecha, String forma, String cantidadStr) throws CampoInvalidoException {
        if (!ValidadorCampos.validarCantidad(cantidadStr)) {
            throw new CampoInvalidoException("Cantidad de colaboraciones inválida: " + cantidadStr);
        }
        int cantidad = Integer.parseInt(cantidadStr);
        for (int i = 0; i < cantidad; i++) {
            Colaboracion colaboracion = new Colaboracion();
            switch (forma) {
                case "DINERO" -> {
                    DonacionDinero donacionDinero = new DonacionDinero();
                    donacionDinero.setColaboracion(colaboracion);
                    colaboracion.setTipo(forma);
                    colaboracion.setColaborador(colaborador);
                    repositorio.agregar(colaboracion);
                }
                case "DONACION_VIANDAS" -> {
                    DonacionVianda donacionVianda = new DonacionVianda();
                    donacionVianda.setColaboracion(colaboracion);
                    colaboracion.setTipo(forma);
                    colaboracion.setColaborador(colaborador);
                    repositorio.agregar(colaboracion);
                }
                case "REDISTRIBUCION_VIANDAS" -> {
                    RedistribucionViandas redistribucionViandas = new RedistribucionViandas();
                    redistribucionViandas.setColaboracion(colaboracion);
                    colaboracion.setTipo(forma);
                    colaboracion.setColaborador(colaborador);
                    repositorio.agregar(colaboracion);
                }
                case "ENTREGA_TARJETAS" -> {
                    RegistrarPersonasVulnerables registrarPersonasVulnerables = new RegistrarPersonasVulnerables();
                    registrarPersonasVulnerables.setColaboracion(colaboracion);
                    colaboracion.setTipo(forma);
                    colaboracion.setColaborador(colaborador);
                    repositorio.agregar(colaboracion);
                }
                default -> throw new CampoInvalidoException("Forma de colaboración inválida: " + forma);
            }
            if (ValidadorCampos.validarFecha(fecha)) {
                colaboracion.setFechaColaboracion(fecha);
            } else {
                throw new CampoInvalidoException("Fecha de colaboración inválida: " + fecha);
            }
        }
    }
}