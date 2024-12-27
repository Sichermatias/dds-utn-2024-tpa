package ar.edu.utn.frba.dds.dominio.archivos.carga_masiva;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.TipoDocumento;
import ar.edu.utn.frba.dds.dominio.persona.TipoPersona;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.TipoRol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.DonacionDineroRepositorio;
import ar.edu.utn.frba.dds.services.ColaboracionService;
import ar.edu.utn.frba.dds.services.TransaccionService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProcesadorCampos implements WithSimplePersistenceUnit {
    public static void procesarCampos(String[] campos) throws CampoInvalidoException {
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
    }
    public static void procesarColaboraciones(Colaborador colaborador, String fecha, String forma, String cantidadStr) throws CampoInvalidoException {

        if (!ValidadorCampos.validarCantidad(cantidadStr)) {
            throw new CampoInvalidoException("Cantidad de colaboraciones inválida: " + cantidadStr);
        }

        int cantidad = Integer.parseInt(cantidadStr);

        Colaboracion colaboracion = new Colaboracion();

        colaboracion.setTipo(forma);
        colaboracion.setFechaHoraAlta(LocalDateTime.now());
        colaboracion.setColaborador(colaborador);
        ColaboracionService colaboracionService = new ColaboracionService(ColaboradorRepositorio.getInstancia(),ColaboracionRepositorio.getInstancia(), DonacionDineroRepositorio.getInstancia(), new TransaccionService() );
        switch (colaboracion.getTipo()) {
            case "DINERO" -> colaboracionService.crearDonacionDinero(colaboracion, cantidad, null);
            case "DONACION_VIANDAS" -> {
                DonacionVianda donacionVianda = new DonacionVianda();
                donacionVianda.setActivo(true);
                donacionVianda.setVianda(null);
                donacionVianda.setFechaHoraAlta(LocalDateTime.now());
                donacionVianda.setColaboracion(colaboracion);
                donacionVianda.setCantViandas(cantidad);
                donacionVianda.setPedidoDeApertura(null);
                Transaccion transaccion = new TransaccionService().crearTransaccion(colaboracion.getColaborador(), donacionVianda.puntaje());
                colaboracion.setTransaccion(transaccion);
                donacionVianda.setColaboracion(colaboracion);
                ColaboracionRepositorio.getInstancia().persistir(donacionVianda);
            }
            case "REDISTRIBUCION_VIANDAS" -> {
                RedistribucionViandas redistribucionViandas = new RedistribucionViandas();
                redistribucionViandas.setHeladeraOrigen(null);
                redistribucionViandas.setHeladeraDestino(null);
                redistribucionViandas.setCantidadViandas(cantidad);
                redistribucionViandas.setMotivoRedistribucion(null);
                redistribucionViandas.setFechaHoraAlta(LocalDateTime.now());
                redistribucionViandas.setPedidoDeAperturaEnDestino(null);
                redistribucionViandas.setPedidoDeAperturaEnOrigen(null);
                Transaccion tran = new TransaccionService().crearTransaccion(colaboracion.getColaborador(), redistribucionViandas.puntaje());
                colaboracion.setTransaccion(tran);
                redistribucionViandas.setColaboracion(colaboracion);
                ColaboracionRepositorio.getInstancia().persistir(redistribucionViandas);
            }
            case "ENTREGA_TARJETAS" -> colaboracionService.crearColaboracionTarjetas(colaboracion, null);
            default -> throw new CampoInvalidoException("Forma de colaboración inválida: " + forma);
        }

            if (ValidadorCampos.validarFecha(fecha)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                colaboracion.setFechaColaboracion(LocalDate.parse(fecha, formatter));
                } else {
                throw new CampoInvalidoException("Fecha de colaboración inválida: " + fecha);
            }

            List<Colaborador> personaGuardada = ColaboracionRepositorio.getInstancia().buscarPorDNI(Colaborador.class, colaborador.getNroDocumento());
            if (personaGuardada.isEmpty() || personaGuardada.get(0).getUsuario()==null) {
                Usuario usuario = new Usuario();
                usuario.setNombreUsuario(colaborador.getNombre()+colaborador.getApellido());
                usuario.setContrasenia(colaborador.getNroDocumento());

                Rol rol = new Rol();
                TipoRol tipoRol = TipoRol.COLABORADOR_HUMANO;
                rol.setTipo(tipoRol);
                rol.setNombreRol("COLABORADOR");
                usuario.setRol(rol);

                colaborador.setUsuario(usuario);
                colaborador.setFechaHoraAlta(LocalDateTime.now());
            } else {
                colaboracion.setColaborador(personaGuardada.get(0));
            }
    }
}