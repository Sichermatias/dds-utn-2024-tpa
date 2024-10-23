package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.infraestructura.Modelo;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.PersonaVulnerable;
import ar.edu.utn.frba.dds.factories.GenericFactory;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColaboracionController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    ColaboracionRepositorio colaboracionRepositorio = ColaboracionRepositorio.getInstancia();
    ColaboradorRepositorio colaboradorRepositorio = ColaboradorRepositorio.getInstancia();
    TransaccionRepositorio transaccionRepositorio = TransaccionRepositorio.getInstancia();
    HeladerasRepositorio heladeraRepositorio= HeladerasRepositorio.getInstancia();
    DonacionDineroRepositorio donacionDineroRepositorio = DonacionDineroRepositorio.getInstancia();

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
            context.render("/colaboraciones/colaboraciones.hbs", model);
        }else context.redirect("/login");
    }

    public void indexNueva(Context context){
        Map<String, Object> model = new HashMap<>();

        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");

        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);
        if (tipoRol!=null){
            switch (tipoRol) {
                case "COLABORADOR_JURIDICO":
                    context.render("colaboraciones/colaboraciones_persona_juridica.hbs", model);
                    break;
                case "COLABORADOR_HUMANO":
                    context.render("colaboraciones/colaboraciones_persona_humana.hbs", model);
                    break;
                default:
                    context.redirect("/login");
                    break;
        }
        }else context.redirect("/login");
    }

    public void indexHistorico(Context context) {
        ColaboradorRepositorio repositorioColaborador = ColaboradorRepositorio.getInstancia();
        ColaboracionRepositorio repositorioColaboracion = ColaboracionRepositorio.getInstancia();

        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId = context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        Colaborador colaborador = repositorioColaborador.obtenerColaboradorPorUsuarioId(usuarioId);

        if (colaborador != null) {
            List<Colaboracion> colaboracionesHistoricas = repositorioColaboracion.obtenerColaboracionesPorColaboradorId(colaborador.getId());
            List<Object> colaboraciones=new ArrayList<>();
            for (Colaboracion colaboracionesHistorica : colaboracionesHistoricas) {
                colaboraciones.add(repositorioColaboracion.obtenerColaboracionPorTipo(colaboracionesHistorica));
            }
            model.put("colaboraciones", colaboraciones);
            context.render("/colaboraciones/colaboraciones_historico.hbs", model);
        } else {
            context.redirect("/login");
        }
    }
    private Colaborador obtenerColaboradorDeSesion(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        return colaboradorRepositorio.buscarPorIdUsuario(usuarioId);
    }

    private Vianda crearVianda(String nombreComida, LocalDate fechaCaducidad, Heladera heladeraAsignada, Double calorias, Double peso) {
        LocalDate fechaDonacion = LocalDate.now();
        Boolean fueEntregado = false;

        Vianda nuevaVianda = new Vianda(nombreComida, fechaCaducidad, fechaDonacion, heladeraAsignada, calorias, peso, fueEntregado);

        return nuevaVianda;
    }

    private Transaccion crearTransaccion(Colaborador colaborador, double puntaje) {
        Transaccion transaccion = new Transaccion();
        transaccion.setColaborador(colaborador);
        transaccion.setMontoPuntaje(puntaje);
        return transaccion;
    }

    private Colaboracion crearColaboracion(String nombre, String tipo, String descripcion, Colaborador colaborador) {
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre(nombre);
        colaboracion.setTipo(tipo);
        colaboracion.setDescripcion(descripcion);
        colaboracion.setFechaColaboracion(LocalDate.now());
        colaboracion.setColaborador(colaborador);
        colaboracion.setFechaHoraAlta(LocalDateTime.now());
        return colaboracion;
    }

    public void colaboracionDinero(Context context) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);
        double monto = Double.parseDouble(context.formParam("monto"));
        Frecuencia frecuencia= Frecuencia.valueOf(context.formParam("frecuencia"));

        Colaboracion colaboracion = crearColaboracion("Donación de Dinero "+ colaborador.getUsuario().getNombreUsuario(), "DINERO", "Descripción donación dinero", colaborador);

        DonacionDinero donacionDinero = new DonacionDinero();
        donacionDinero.setMonto(monto);
        donacionDinero.setFrecuencia(frecuencia);
        donacionDinero.setFechaHoraAlta(LocalDateTime.now());
        Transaccion transaccion = crearTransaccion(colaborador, donacionDinero.puntaje());
        colaboracion.setTransaccion(transaccion);
        donacionDinero.setColaboracion(colaboracion);
        donacionDineroRepositorio.persistir(donacionDinero);

        context.redirect("/colaboraciones");
        }

    public void colaboracionVianda(Context context) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);
        Colaboracion colaboracion = crearColaboracion("Donación de Vianda "+ colaborador.getUsuario().getNombreUsuario(), "DONACION_VIANDAS", "Descripción viandas", colaborador);

        String nombreComida = context.formParam("nombreComida");
        LocalDate fechaCaducidad = LocalDate.parse(context.formParam("fechaCaducidad"));
        Heladera heladeraAsignada = heladeraRepositorio.buscarPorId(Heladera.class, Long.parseLong(context.formParam("heladeraAsignada")));
        Double calorias = Double.parseDouble(context.formParam("calorias"));
        Double peso = Double.parseDouble(context.formParam("peso"));

        Vianda nuevaVianda = crearVianda(nombreComida,fechaCaducidad,heladeraAsignada,calorias,peso);

        DonacionVianda donacionVianda = new DonacionVianda();
        donacionVianda.setActivo(true);
        donacionVianda.setVianda(nuevaVianda);
        donacionVianda.setFechaHoraAlta(LocalDateTime.now());

        Transaccion transaccion = crearTransaccion(colaborador, donacionVianda.puntaje());
        colaboracion.setTransaccion(transaccion);

        donacionVianda.setColaboracion(colaboracion);

        colaboracionRepositorio.persist(donacionVianda);

        context.redirect("/colaboraciones");
    }
    public void colaboracionDistribucion(Context context) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);
        Colaboracion colaboracion = crearColaboracion("Redistribución de Viandas","REDISTRIBUCION_VIANDAS","Descripción redistribución",colaborador);

        RedistribucionViandas redistribucionViandas = new RedistribucionViandas();

        Heladera heladeraOrigen =   heladeraRepositorio.buscarPorId(Heladera.class , Long.parseLong(context.formParam("heladeraOrigenId")));
        Heladera heladeraDestino = heladeraRepositorio.buscarPorId(Heladera.class, Long.parseLong(context.formParam("heladeraDestinoId")));
        Integer cantidadViandas = Integer.parseInt(context.formParam("cantidadViandas"));
        MotivoRedistribucion motivo = new MotivoRedistribucion(context.formParam("motivoRedistribucion"));

        redistribucionViandas.setHeladeraOrigen(heladeraOrigen);
        redistribucionViandas.setHeladeraDestino(heladeraDestino);
        redistribucionViandas.setCantidadViandas(cantidadViandas);
        redistribucionViandas.setMotivoRedistribucion(motivo);
        redistribucionViandas.setFechaHoraAlta(LocalDateTime.now());

        Transaccion transaccion = crearTransaccion(colaborador, redistribucionViandas.puntaje());
        colaboracion.setTransaccion(transaccion);

        redistribucionViandas.setColaboracion(colaboracion);

        colaboracionRepositorio.persist(redistribucionViandas);

        context.redirect("/colaboraciones");
    }
    public void colaboracionTarjetas(Context context, PersonaVulnerable personaVulnerable) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);

        Colaboracion colaboracion = crearColaboracion("Entrega de Tarjetas","ENTREGA_TARJETAS","Descripción entrega tarjetas",colaborador);

        RegistrarPersonasVulnerables registrarPersonasVulnerables = new RegistrarPersonasVulnerables();
        registrarPersonasVulnerables.setPersonaVulnerable(personaVulnerable);
        registrarPersonasVulnerables.setFechaHoraAlta(LocalDateTime.now());

        Transaccion transaccion = crearTransaccion(colaborador, registrarPersonasVulnerables.puntaje());
        colaboracion.setTransaccion(transaccion);

        registrarPersonasVulnerables.setColaboracion(colaboracion);


        colaboracionRepositorio.persist(registrarPersonasVulnerables);

        context.redirect("/colaboraciones");
    }
    public void colaboracionHeladera(Context context) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);

        String nombreHeladera = context.formParam("nombreHeladera");
        Integer cantMaxViandas = Integer.parseInt(context.formParam("cantMaxViandas"));
        String latitud = context.formParam("latitud");
        String longitud =context.formParam("longitud");
        String direccion =context.formParam("direccion");
        String modelo =context.formParam("modeloHeladera");

        Colaboracion colaboracion = crearColaboracion("Hostear Heladera", "HOSTEAR_HELADERA", "Colaboración para hostear heladera", colaborador);

        Heladera heladera = new Heladera();
        heladera.setNombre(nombreHeladera);
        heladera.setCantMaxViandas(cantMaxViandas);
        Ubicacion ubicacion=new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setLongitud(longitud);
        ubicacion.setLatitud(latitud);
        heladera.setUbicacion(ubicacion);
        Modelo modeloHeladera=new Modelo();
        modeloHeladera.setNombre(modelo);
        modeloHeladera.setTempMaxAceptable(10.0);
        modeloHeladera.setTempMinAceptable(0.0);
        heladera.setModelo(modeloHeladera);
        heladera.setFechaPuestaEnMarcha(LocalDate.now());
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.now());

        HostearHeladera hostearHeladera = new HostearHeladera();
        hostearHeladera.setHeladera(heladera);
        hostearHeladera.setEnVigencia(true);
        hostearHeladera.setFechaHoraAlta(LocalDateTime.now());

        Transaccion transaccion = crearTransaccion(colaborador, hostearHeladera.puntaje());
        colaboracion.setTransaccion(transaccion);
        hostearHeladera.setColaboracion(colaboracion);

        colaboracionRepositorio.persist(hostearHeladera);

        context.redirect("/colaboraciones");
    }
    public void colaboracionPremio(Context context){
    }
    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
