package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.dominio.colaboracion.OfrecerPremio;
import ar.edu.utn.frba.dds.dominio.colaboracion.Premio;
import ar.edu.utn.frba.dds.dominio.colaboracion.RubroPremio;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.PremioRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PremiosController extends Controller implements ICrudViewsHandler {
    private final ColaboradorRepositorio colaboradorRepositorio;

    private final PremioRepositorio premioRepositorio;

    private final ColaboracionRepositorio colaboracionRepositorio;

    public PremiosController(ColaboradorRepositorio colaboradorRepositorio, PremioRepositorio premioRepositorio, ColaboracionRepositorio colaboracionRepositorio) {
        this.colaboradorRepositorio = colaboradorRepositorio;
        this.premioRepositorio = premioRepositorio;
        this.colaboracionRepositorio = colaboracionRepositorio;
    }

    @Override
    public void index(Context context) {
        Usuario usuario = this.usuarioLogueado(context);
        Map<String, Object> model = new HashMap<>();
        Long usuarioId= context.sessionAttribute("usuario_id");
        String tipoRol = context.sessionAttribute("tipo_rol");
        if (usuarioId != null) {
            Colaborador colaborador = this.colaboradorRepositorio.buscarPorIdUsuario(usuario.getId());
            model.put("usuario_id", usuarioId);
            model.put("tipo_rol", tipoRol);
            model.put("puntaje", calcularPuntaje(colaborador));
            List<Premio> premios = this.premioRepositorio.buscarTodos(Premio.class);
            model.put("premios", premios);
            List<String> rubros = this.premioRepositorio.buscarTodosLosRubros();
            model.put("rubros", rubros);
            context.render("puntos_y_premios.hbs", model);
        }else context.redirect("/login?return=puntos-y-premios");
    }
    public Integer calcularPuntaje(Colaborador colaborador) {
        List<Colaboracion> colaboraciones = ColaboracionRepositorio.getInstancia().obtenerColaboracionesPorColaboradorId(colaborador.getId());
        int acumulador = 0;

        for (Colaboracion colaboracion : colaboraciones) {
            if ("HOSTEAR_HELADERA".equals(colaboracion.getTipo())) {
                LocalDate fechaActual = LocalDate.now();
                long mesesActiva = ChronoUnit.MONTHS.between(colaboracion.getFechaColaboracion(), fechaActual);
                acumulador += mesesActiva * 5;
            }

            acumulador += colaboracion.getTransaccion().getMontoPuntaje();
        }
        colaborador.setPuntaje(acumulador);
        return acumulador;
    }

    @Override
    public void show(Context context) {
    }
    @Override
    public void create(Context context) {

    }
    @Override
    public void save(Context context) {
        Usuario usuario = this.usuarioLogueado(context);
        Colaborador colaborador = this.colaboradorRepositorio.buscarPorIdUsuario(usuario.getId());

        String nombre = context.formParam("nombre");
        RubroPremio rubroPremio = this.premioRepositorio
                .buscarRubroPorNombre(context.formParam("rubro"));
        Integer cantidadPuntosNecesarios = Integer.valueOf(Objects.requireNonNull(context.formParam("cantPuntosNecesarios")));
        Integer cantidadDonada = Integer.valueOf(Objects.requireNonNull(context.formParam("cantDonada")));
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        //TODO: investigar cómo subir una imagen

        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre("Donacion de premio: " + nombre + ", " + cantidadDonada + " unidad/es.");
        colaboracion.setTipo("PREMIO");
        colaboracion.setFechaColaboracion(LocalDate.from(fechaHoraActual));
        colaboracion.setFechaHoraAlta(fechaHoraActual);
        colaboracion.setColaborador(colaborador);

        Premio premio = new Premio();
        premio.setNombre(nombre);
        premio.setRubro(rubroPremio);
        premio.setCantidadDisponible(cantidadDonada);
        premio.setCantidadPuntosNecesarios(cantidadPuntosNecesarios);
        premio.setFechaHoraAlta(fechaHoraActual);
        premio.setActivo(true);

        OfrecerPremio ofrecerPremio = new OfrecerPremio();
        ofrecerPremio.setPremio(premio);
        ofrecerPremio.setCantidad(cantidadDonada);
        ofrecerPremio.setColaboracion(colaboracion);
        ofrecerPremio.setFechaHoraAlta(fechaHoraActual);
        ofrecerPremio.setActivo(true);

        this.colaboracionRepositorio.persistir(ofrecerPremio);

        context.redirect("/colaboraciones/premio?enviado=" + true);
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
