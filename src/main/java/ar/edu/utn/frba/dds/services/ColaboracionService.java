package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.infraestructura.Modelo;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.DonacionDineroRepositorio;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ColaboracionService {
    private final ColaboracionRepositorio colaboracionRepositorio;
    private final DonacionDineroRepositorio donacionDineroRepositorio;
    private final TransaccionService transaccionService;

    public ColaboracionService(ColaboracionRepositorio colaboracionRepositorio, DonacionDineroRepositorio donacionDineroRepositorio, TransaccionService transaccionService) {
        this.colaboracionRepositorio = colaboracionRepositorio;
        this.donacionDineroRepositorio = donacionDineroRepositorio;
        this.transaccionService=transaccionService;
    }

    public Colaboracion crearColaboracion(String nombre, String tipo, String descripcion, Colaborador colaborador) {
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre(nombre);
        colaboracion.setTipo(tipo);
        colaboracion.setDescripcion(descripcion);
        colaboracion.setFechaColaboracion(LocalDate.now());
        colaboracion.setColaborador(colaborador);
        colaboracion.setFechaHoraAlta(LocalDateTime.now());
        return colaboracion;
    }

    public void crearDonacionDinero(Colaboracion colaboracion, double monto, Frecuencia frecuencia) {
        DonacionDinero donacionDinero = new DonacionDinero();
        donacionDinero.setMonto(monto);
        donacionDinero.setFrecuencia(frecuencia);
        donacionDinero.setFechaHoraAlta(LocalDateTime.now());
        Transaccion transaccion = transaccionService.crearTransaccion(colaboracion.getColaborador(),donacionDinero.puntaje());
        colaboracion.setTransaccion(transaccion);
        donacionDinero.setColaboracion(colaboracion);
        donacionDineroRepositorio.persistir(donacionDinero);
    }

    public Vianda crearVianda(String nombreComida, LocalDate fechaCaducidad, Heladera heladeraAsignada, Double calorias, Double peso) {
        LocalDate fechaDonacion = LocalDate.now();
        LocalDateTime fechaAlta=LocalDateTime.now();
        Boolean fueEntregado = false;
        Vianda nuevaVianda = new Vianda(nombreComida, fechaCaducidad, fechaDonacion, heladeraAsignada, calorias, peso, fueEntregado);
        nuevaVianda.setFechaHoraAlta(fechaAlta);
        return nuevaVianda;
    }

    public void crearDonacionVianda(Colaboracion colaboracion, Vianda vianda, Heladera heladeraAsignada, Double cantidadViandas) {
        DonacionVianda donacionVianda = new DonacionVianda();
        donacionVianda.setActivo(true);
        donacionVianda.setVianda(vianda);
        donacionVianda.setFechaHoraAlta(LocalDateTime.now());

        PedidoDeApertura pedidoDeApertura= new PedidoDeApertura();
        pedidoDeApertura.setHeladera(heladeraAsignada);
        pedidoDeApertura.setMotivo("Donacion de viandas");
        pedidoDeApertura.setTarjeta(colaboracion.getColaborador().getTarjetas().get(0));
        pedidoDeApertura.setFechaHoraRealizada(LocalDateTime.now());
        pedidoDeApertura.setFechaHoraAlta(LocalDateTime.now());

        donacionVianda.setCantViandas(cantidadViandas);
        donacionVianda.setPedidoDeApertura(pedidoDeApertura);

        Transaccion transaccion = transaccionService.crearTransaccion(colaboracion.getColaborador(),donacionVianda.puntaje());
        colaboracion.setTransaccion(transaccion);
        donacionVianda.setColaboracion(colaboracion);

        heladeraAsignada.recibirDonacionVianda(donacionVianda);

        colaboracionRepositorio.persistir(donacionVianda);
    }

    public Ubicacion crearUbicacion(String direccion, String longitud, String latitud){
        Ubicacion ubicacion=new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLongitud(longitud);
        ubicacion.setLatitud(latitud);
        return ubicacion;
    }
    public Modelo crearModelo(String modelo, Double tempMax, Double tempMin){
        Modelo modeloHeladera=new Modelo();
        modeloHeladera.setNombre(modelo);
        modeloHeladera.setTempMaxAceptable(tempMax);
        modeloHeladera.setTempMinAceptable(tempMin);
        return modeloHeladera;
    }

    public Heladera crearHeladera(String nombreHeladera, Integer cantMaxViandas, Ubicacion ubicacion, Modelo modeloHeladera){
        Heladera heladera = new Heladera();
        heladera.setNombre(nombreHeladera);
        heladera.setCantMaxViandas(cantMaxViandas);
        heladera.setUbicacion(ubicacion);
        heladera.setModelo(modeloHeladera);
        heladera.setFechaPuestaEnMarcha(LocalDate.now());
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.now());
        return heladera;
    }

    public void crearHostearHeladera(Colaboracion colaboracion, Heladera heladera) {
        HostearHeladera hostearHeladera = new HostearHeladera();
        hostearHeladera.setHeladera(heladera);
        hostearHeladera.setFechaHoraAlta(LocalDateTime.now());
        hostearHeladera.setEnVigencia(true);

        Transaccion transaccion = transaccionService.crearTransaccion(colaboracion.getColaborador(), hostearHeladera.puntaje());

        colaboracion.setTransaccion(transaccion);
        hostearHeladera.setColaboracion(colaboracion);
        colaboracionRepositorio.persistir(hostearHeladera);

    }

    public void crearRedistribucionViandas(Colaboracion colaboracion, Heladera heladeraOrigen, Heladera heladeraDestino, int cantidadViandas, MotivoRedistribucion motivo) {
        heladeraOrigen.setCantViandasActuales(heladeraOrigen.getCantViandasActuales()-cantidadViandas);
        heladeraDestino.setCantViandasActuales(heladeraDestino.getCantViandasActuales()+cantidadViandas);

        RedistribucionViandas redistribucionViandas=new RedistribucionViandas();
        redistribucionViandas.setHeladeraOrigen(heladeraOrigen);
        redistribucionViandas.setHeladeraDestino(heladeraDestino);
        redistribucionViandas.setCantidadViandas(cantidadViandas);
        redistribucionViandas.setMotivoRedistribucion(motivo);
        redistribucionViandas.setFechaHoraAlta(LocalDateTime.now());

        PedidoDeApertura pedidoOrigen=new PedidoDeApertura();
        PedidoDeApertura pedidoDestino=new PedidoDeApertura();

        pedidoOrigen.setFechaHoraAlta(LocalDateTime.now());
        pedidoOrigen.setMotivo("Redistribucion"+motivo.getDescripcion());
        pedidoOrigen.setHeladera(heladeraOrigen);
        pedidoOrigen.setValido(true);
        pedidoOrigen.setTarjeta(colaboracion.getColaborador().getTarjetas().get(0));

        pedidoDestino.setFechaHoraAlta(LocalDateTime.now());
        pedidoDestino.setMotivo("Redistribucion"+motivo.getDescripcion());
        pedidoDestino.setHeladera(heladeraDestino);
        pedidoDestino.setValido(true);
        pedidoDestino.setTarjeta(colaboracion.getColaborador().getTarjetas().get(0));

        redistribucionViandas.setPedidoDeAperturaEnDestino(pedidoDestino);
        redistribucionViandas.setPedidoDeAperturaEnOrigen(pedidoOrigen);

        Transaccion transaccion = transaccionService.crearTransaccion(colaboracion.getColaborador(), redistribucionViandas.puntaje());
        colaboracion.setTransaccion(transaccion);

        redistribucionViandas.setColaboracion(colaboracion);

        colaboracionRepositorio.persistir(redistribucionViandas);
    }

    public void crearColaboracionTarjetas(Colaboracion colaboracion, PersonaVulnerable personaVulnerable) {
        RegistrarPersonasVulnerables registrarPersonasVulnerables = new RegistrarPersonasVulnerables();
        registrarPersonasVulnerables.setPersonaVulnerable(personaVulnerable);
        registrarPersonasVulnerables.setFechaHoraAlta(LocalDateTime.now());

        Transaccion transaccion = transaccionService.crearTransaccion(colaboracion.getColaborador(), registrarPersonasVulnerables.puntaje());

        colaboracion.setTransaccion(transaccion);
        registrarPersonasVulnerables.setColaboracion(colaboracion);

        colaboracionRepositorio.persistir(registrarPersonasVulnerables);
    }
}
