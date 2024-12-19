package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.infraestructura.Modelo;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.PersonaVulnerable;
import ar.edu.utn.frba.dds.dominio.reportes.ViandasDonadasPorColaborador;
import ar.edu.utn.frba.dds.dtos.georef.ProvinciaMunicipioGeorefDTO;
import ar.edu.utn.frba.dds.dtos.georef.PuntoGeorefDTO;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.DonacionDineroRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ViandasDonadasColaboradorRepositorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ColaboracionService {
    private final ColaboradorRepositorio colaboradorRepositorio;
    private final ColaboracionRepositorio colaboracionRepositorio;
    private final DonacionDineroRepositorio donacionDineroRepositorio;
    private final TransaccionService transaccionService;

    public ColaboracionService(ColaboradorRepositorio colaboradorRepositorio, ColaboracionRepositorio colaboracionRepositorio, DonacionDineroRepositorio donacionDineroRepositorio, TransaccionService transaccionService) {
        this.colaboradorRepositorio = colaboradorRepositorio;
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
        Colaborador colaborador = colaboracion.getColaborador();
        DonacionDinero donacionDinero = new DonacionDinero();
        donacionDinero.setMonto(monto);
        donacionDinero.setFrecuencia(frecuencia);
        donacionDinero.setFechaHoraAlta(LocalDateTime.now());
        Transaccion transaccion = transaccionService.crearTransaccion(colaborador,donacionDinero.puntaje());
        colaboracion.setTransaccion(transaccion);
        donacionDinero.setColaboracion(colaboracion);
        donacionDineroRepositorio.persistir(donacionDinero);
        colaboradorRepositorio.actualizar(colaborador);
    }

    public Vianda crearVianda(String nombreComida, LocalDate fechaCaducidad, Heladera heladeraAsignada, Double calorias, Double peso) {
        LocalDate fechaDonacion = LocalDate.now();
        LocalDateTime fechaAlta=LocalDateTime.now();
        Boolean fueEntregado = false;
        Vianda nuevaVianda = new Vianda(nombreComida, fechaCaducidad, fechaDonacion, heladeraAsignada, calorias, peso, fueEntregado);
        nuevaVianda.setFechaHoraAlta(fechaAlta);
        return nuevaVianda;
    }
    public void crearDonacionVianda(Colaboracion colaboracion, Vianda vianda, Heladera heladeraAsignada, Integer cantidadViandas) {
        Colaborador colaborador = colaboracion.getColaborador();

        if (cantidadViandas == null || cantidadViandas <= 0) {
            throw new IllegalArgumentException("La cantidad de viandas debe ser mayor a cero.");
        }
        DonacionVianda donacionVianda = new DonacionVianda();
        donacionVianda.setActivo(true);
        donacionVianda.setVianda(vianda);
        donacionVianda.setFechaHoraAlta(LocalDateTime.now());
        donacionVianda.setColaboracion(colaboracion);
        PedidoDeApertura pedidoDeApertura = new PedidoDeApertura();
        pedidoDeApertura.setHeladera(heladeraAsignada);
        pedidoDeApertura.setMotivo("Donación de viandas");
        pedidoDeApertura.setTarjeta(colaboracion.getColaborador().getTarjetas().get(0));
        pedidoDeApertura.setFechaHoraRealizada(LocalDateTime.now());
        pedidoDeApertura.setFechaHoraAlta(LocalDateTime.now());
        pedidoDeApertura.setCantidadViandas(donacionVianda.getCantViandas());

        donacionVianda.setCantViandas(cantidadViandas);
        donacionVianda.setPedidoDeApertura(pedidoDeApertura);

        heladeraAsignada.recibirDonacionVianda(donacionVianda);
        heladeraAsignada.setCantSemanalViandasColocadas(
                heladeraAsignada.getCantSemanalViandasColocadas() + cantidadViandas.intValue()
        );
        colaborador.setCantSemanalViandasDonadas(
                colaborador.getCantSemanalViandasDonadas() + cantidadViandas.intValue()
        );
        Transaccion transaccion= transaccionService.crearTransaccion(colaborador, donacionVianda.puntaje());
        colaboracion.setTransaccion(transaccion);
        colaboracionRepositorio.persistir(donacionVianda);
        colaboradorRepositorio.actualizar(colaborador);

        ViandasDonadasColaboradorRepositorio viandasRepo = ViandasDonadasColaboradorRepositorio.getInstancia();
        List<ViandasDonadasPorColaborador> viandasDonadasList = viandasRepo.buscarPorColaboradorId(
                ViandasDonadasPorColaborador.class, colaborador.getId()
        );

        ViandasDonadasPorColaborador viandasDonadas;
        if (!viandasDonadasList.isEmpty()) {
            viandasDonadas = viandasDonadasList.get(0);
        } else {
            viandasDonadas = new ViandasDonadasPorColaborador(colaborador, LocalDate.now());
        }
        viandasDonadas.viandasDonadasSemanal();
        viandasRepo.persistir(viandasDonadas);
    }

    public Ubicacion crearUbicacion(String direccion, String longitud, String latitud){
        Ubicacion ubicacion=new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLongitud(longitud);
        ubicacion.setLatitud(latitud);

        //Traer la localidad y la provincia con georef
        Localidad localidad = null;
        try {
            ProvinciaMunicipioGeorefDTO provinciaMunicipio = GeorefService.getProvinciaMunicipio(new PuntoGeorefDTO(latitud, longitud));

            LocalidadService localidadService = new LocalidadService();
            localidad = localidadService.getLocalidad(provinciaMunicipio);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        ubicacion.setLocalidad(localidad);

        return ubicacion;
    }
    public Modelo crearModelo(String modelo, Double tempMax, Double tempMin){
        Modelo modeloHeladera=new Modelo();
        modeloHeladera.setNombre(modelo);
        modeloHeladera.setTempMaxAceptable(tempMax);
        modeloHeladera.setTempMinAceptable(tempMin);
        modeloHeladera.setFechaHoraAlta(LocalDateTime.now());
        return modeloHeladera;
    }

    public Heladera crearHeladera(String nombreHeladera, Integer cantMaxViandas, Ubicacion ubicacion, Modelo modeloHeladera, Colaborador colaborador){
        Heladera heladera = new Heladera();
        heladera.setNombre(nombreHeladera);
        heladera.setCantMaxViandas(cantMaxViandas);
        heladera.setUbicacion(ubicacion);
        heladera.setModelo(modeloHeladera);
        heladera.setFechaPuestaEnMarcha(LocalDate.now());
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.now());
        heladera.setFechaHoraAlta(LocalDateTime.now());
        heladera.setColaborador(colaborador);
        return heladera;
    }

    public void crearHostearHeladera(Colaboracion colaboracion, Heladera heladera) {
        HostearHeladera hostearHeladera = new HostearHeladera();
        hostearHeladera.setHeladera(heladera);
        hostearHeladera.setFechaHoraAlta(LocalDateTime.now());
        hostearHeladera.setEnVigencia(true);

        Colaborador colaborador = colaboracion.getColaborador();
        Integer cantHeladerasHosteadas = colaborador.getCantHeladerasHosteadasActual();
        colaborador.setCantHeladerasHosteadasActual(cantHeladerasHosteadas + 1);

        Transaccion transaccion = transaccionService.crearTransaccion(colaborador, hostearHeladera.puntaje());

        colaboracion.setTransaccion(transaccion);
        hostearHeladera.setColaboracion(colaboracion);
        colaboracionRepositorio.persistir(hostearHeladera);
        colaboradorRepositorio.actualizar(colaborador);
    }

    public void crearRedistribucionViandas(Colaboracion colaboracion, Heladera heladeraOrigen, Heladera heladeraDestino, int cantidadViandas, MotivoRedistribucion motivo) {
        Colaborador colaborador = colaboracion.getColaborador();
        heladeraOrigen.setCantViandasActuales(heladeraOrigen.getCantViandasActuales()-cantidadViandas);
        heladeraDestino.setCantViandasActuales(heladeraDestino.getCantViandasActuales()+cantidadViandas);
        heladeraDestino.setCantSemanalViandasColocadas(heladeraDestino.getCantSemanalViandasColocadas()+cantidadViandas);
        heladeraOrigen.setCantSemanalViandasRetiradas(heladeraOrigen.getCantSemanalViandasRetiradas()+cantidadViandas);

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
        pedidoOrigen.setTarjeta(colaborador.getTarjetas().get(0));
        pedidoOrigen.setCantidadViandas(-cantidadViandas);

        pedidoDestino.setFechaHoraAlta(LocalDateTime.now());
        pedidoDestino.setMotivo("Redistribucion"+motivo.getDescripcion());
        pedidoDestino.setHeladera(heladeraDestino);
        pedidoDestino.setValido(true);
        pedidoDestino.setTarjeta(colaborador.getTarjetas().get(0));
        pedidoDestino.setCantidadViandas(cantidadViandas);

        redistribucionViandas.setPedidoDeAperturaEnDestino(pedidoDestino);
        redistribucionViandas.setPedidoDeAperturaEnOrigen(pedidoOrigen);

        Transaccion transaccion = transaccionService.crearTransaccion(colaborador, redistribucionViandas.puntaje());
        colaboracion.setTransaccion(transaccion);

        redistribucionViandas.setColaboracion(colaboracion);

        colaboracionRepositorio.persistir(redistribucionViandas);
        colaboradorRepositorio.actualizar(colaborador);
    }

    public void crearColaboracionTarjetas(Colaboracion colaboracion, PersonaVulnerable personaVulnerable) {
        Colaborador colaborador = colaboracion.getColaborador();
        RegistrarPersonasVulnerables registrarPersonasVulnerables = new RegistrarPersonasVulnerables();
        registrarPersonasVulnerables.setPersonaVulnerable(personaVulnerable);
        registrarPersonasVulnerables.setFechaHoraAlta(LocalDateTime.now());

        Transaccion transaccion = transaccionService.crearTransaccion(colaborador, registrarPersonasVulnerables.puntaje());

        colaboracion.setTransaccion(transaccion);
        registrarPersonasVulnerables.setColaboracion(colaboracion);

        colaboracionRepositorio.persistir(registrarPersonasVulnerables);
        colaboradorRepositorio.actualizar(colaborador);
    }

    public void actualizarDiasSinAcumularPuntajeHosteoHeladera() {
        List<HostearHeladera> hosteosHeladera = this.colaboracionRepositorio.buscarHosteosNoContadosParaElPuntajeEnMasDeUnDia();
        if(hosteosHeladera.isEmpty())
            return;

        for(HostearHeladera hosteoHeladera: hosteosHeladera) {
            LocalDate fechaDeHoy = LocalDate.now();

            Heladera heladera = hosteoHeladera.getHeladera();
            Integer cantDiasSinContarParaPuntaje = heladera.getCantDiasSinContarParaPuntaje();
            heladera.setCantDiasSinContarParaPuntaje(cantDiasSinContarParaPuntaje + 1);

            heladera.setUltimaFechaContadaParaPuntaje(fechaDeHoy);

            this.colaboracionRepositorio.actualizar(hosteoHeladera);
        }
    }

    public void actualizarPuntajeColaboradoresPorHosteoHeladera() {
        List<HostearHeladera> hosteosHeladera = this.colaboracionRepositorio.buscarHosteosNoContadosParaElPuntajeEn(30);
        if(hosteosHeladera.isEmpty())
            return;

        Double puntosHostearHeladera = hosteosHeladera.get(0).puntaje();
        for(HostearHeladera hosteoHeladera: hosteosHeladera) {
            Colaborador colaborador = hosteoHeladera.getColaboracion().getColaborador();
            Integer cantHeladerasHosteadas = colaborador.getCantHeladerasHosteadasActual();

            colaborador.actualizarPuntajeSumandole(cantHeladerasHosteadas * puntosHostearHeladera);

            Heladera heladera = hosteoHeladera.getHeladera();
            heladera.setCantDiasSinContarParaPuntaje(0);

            //TODO: crear transaccion para actualizacion de puntaje

            this.colaboracionRepositorio.actualizar(hosteoHeladera);
        }
    }
}
