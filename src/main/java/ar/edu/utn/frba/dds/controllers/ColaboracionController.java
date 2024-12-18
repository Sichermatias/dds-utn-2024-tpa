package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.infraestructura.Modelo;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.services.ColaboracionService;
import ar.edu.utn.frba.dds.services.TransaccionService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColaboracionController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private final ColaboradorRepositorio colaboradorRepositorio;
    private final HeladerasRepositorio heladeraRepositorio;

    private final PremioRepositorio premioRepositorio;

    private final ColaboracionService colaboracionService;
    private final ModeloRepositorio modeloRepositorio;

    public ColaboracionController(
            ColaboradorRepositorio colaboradorRepositorio,
            HeladerasRepositorio heladeraRepositorio,
            PremioRepositorio premioRepositorio,
            ColaboracionService colaboracionService, ModeloRepositorio modeloRepositorio) {
        this.colaboradorRepositorio = colaboradorRepositorio;
        this.heladeraRepositorio = heladeraRepositorio;
        this.premioRepositorio = premioRepositorio;
        this.colaboracionService = colaboracionService;
        this.modeloRepositorio= modeloRepositorio;
    }
    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
            context.render("colaboraciones/colaboraciones.hbs", model);
        }else context.redirect("/login");
    }

    public void indexNueva(Context context){
        Map<String, Object> model = new HashMap<>();

        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        List<Heladera> heladeras = heladeraRepositorio.buscarTodas();
        List<Modelo> modelos= modeloRepositorio.buscarTodos(Modelo.class);
        model.put("modelos", modelos);
        model.put("heladeras", heladeras);
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);
        if (tipoRol!=null){
            switch (tipoRol) {
                case "COLABORADOR_JURIDICO" ->
                        context.render("colaboraciones/colaboraciones_persona_juridica.hbs", model);
                case "COLABORADOR_HUMANO" -> context.render("colaboraciones/colaboraciones_persona_humana.hbs", model);
                default -> context.redirect("/login");
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
            context.render("colaboraciones/colaboraciones_historico.hbs", model);
        } else {
            context.redirect("/login");
        }
    }

    public void indexColaboracionPremio(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);

            List<String> rubros = this.premioRepositorio.buscarTodosLosRubros();
            model.put("rubros", rubros);

            boolean enviado = Boolean.parseBoolean(context.queryParam("enviado"));
            model.put("enviado", enviado);

            context.render("colaboraciones/formularios/colaboracion_ofrecer_premio.hbs", model);
        }else context.redirect("/login");
    }
    private Colaborador obtenerColaboradorDeSesion(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        return colaboradorRepositorio.buscarPorIdUsuario(usuarioId);
    }
    public void colaboracionDinero(Context context) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);
        double monto = Double.parseDouble(context.formParam("monto"));
        Frecuencia frecuencia= Frecuencia.valueOf(context.formParam("frecuencia"));

        Colaboracion colaboracion = colaboracionService.crearColaboracion("Donación de Dinero", "DINERO", "Descripción donación dinero", colaborador);
        colaboracionService.crearDonacionDinero(colaboracion, monto, frecuencia);

        context.redirect("/colaboraciones");
    }

    public void colaboracionVianda(Context context) {

        Colaborador colaborador = obtenerColaboradorDeSesion(context);
        Colaboracion colaboracion = colaboracionService.crearColaboracion("Donación de Vianda", "DONACION_VIANDAS", "Descripción viandas", colaborador);

        String nombreComida = context.formParam("nombreComida");
        LocalDate fechaCaducidad = LocalDate.parse(context.formParam("fechaCaducidad"));
        Heladera heladeraAsignada = heladeraRepositorio.buscarPorId(Heladera.class, Long.parseLong(context.formParam("heladera")));
        Double calorias = Double.parseDouble(context.formParam("calorias"));
        Double peso = Double.parseDouble(context.formParam("peso"));
        Integer cantidadViandas = Integer.parseInt(context.formParam("cantidad"));

        Vianda vianda=colaboracionService.crearVianda(nombreComida,fechaCaducidad,heladeraAsignada,calorias,peso);
        colaboracionService.crearDonacionVianda(colaboracion, vianda, heladeraAsignada, cantidadViandas);

        context.redirect("/colaboraciones");
    }
    public void colaboracionDistribucion(Context context) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);
        Colaboracion colaboracion = colaboracionService.crearColaboracion("Redistribución de Viandas", "REDISTRIBUCION_VIANDAS", "Descripción redistribución", colaborador);

        Heladera heladeraOrigen =   heladeraRepositorio.buscarPorId(Heladera.class , Long.parseLong(context.formParam("heladeraOrigen")));
        Heladera heladeraDestino = heladeraRepositorio.buscarPorId(Heladera.class, Long.parseLong(context.formParam("heladeraDestino")));
        Integer cantidadViandas = Integer.parseInt(context.formParam("cantidadViandas"));
        MotivoRedistribucion motivo = new MotivoRedistribucion(context.formParam("motivoRedistribucion"));

        colaboracionService.crearRedistribucionViandas(colaboracion, heladeraOrigen, heladeraDestino, cantidadViandas,motivo);

        context.redirect("/colaboraciones");
    }

    public void colaboracionTarjetas(Context context, PersonaVulnerable personaVulnerable) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);
        Colaboracion colaboracion = colaboracionService.crearColaboracion("Entrega de Tarjetas","ENTREGA_TARJETAS","Descripción entrega tarjetas",colaborador);
        colaboracionService.crearColaboracionTarjetas(colaboracion, personaVulnerable);

        context.redirect("/colaboraciones");
    }
    public void colaboracionHeladera(Context context) {
        Colaborador colaborador = obtenerColaboradorDeSesion(context);
        Colaboracion colaboracion = colaboracionService.crearColaboracion("Hostear Heladera", "HOSTEAR_HELADERA", "Descripción hosteo heladera", colaborador);

        String nombreHeladera = context.formParam("nombreHeladera");
        Integer cantMaxViandas = Integer.parseInt(context.formParam("cantMaxViandas"));
        String latitud = context.formParam("lat");
        String longitud =context.formParam("lng");
        String direccion =context.formParam("direccion");
        String nombreModelo =context.formParam("modeloHeladera");
        Double tempMax = Double.parseDouble(context.formParam("tempMax"));
        Double tempMin = Double.parseDouble(context.formParam("tempMin"));

        List<Modelo> modeloG=modeloRepositorio.buscarPorNombre(nombreModelo);
        Ubicacion ubicacion= colaboracionService.crearUbicacion(direccion,longitud,latitud);
        Modelo modelo;
        if(modeloG.isEmpty()){modelo= colaboracionService.crearModelo(nombreModelo, tempMax,tempMin);}
        else{modelo=modeloG.get(0);
        }
        Heladera heladera = colaboracionService.crearHeladera(nombreHeladera, cantMaxViandas, ubicacion, modelo, colaborador);

        colaboracionService.crearHostearHeladera(colaboracion, heladera);

        context.redirect("/colaboraciones");
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
