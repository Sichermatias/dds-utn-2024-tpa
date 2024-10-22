package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.dominio.colaboracion.HostearHeladera;
import ar.edu.utn.frba.dds.dominio.colaboracion.Transaccion;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.factories.GenericFactory;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.TransaccionRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColaboracionController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    ColaboracionRepositorio colaboracionRepositorio = ColaboracionRepositorio.getInstancia();
    ColaboradorRepositorio colaboradorRepositorio = ColaboradorRepositorio.getInstancia();
    TransaccionRepositorio transaccionRepositorio = TransaccionRepositorio.getInstancia();

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
            context.render("/colaboraciones/colaboraciones.hbs", model);
        }else context.render("/login", model);
    }
    public void indexNueva(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);
            switch (tipoRol) {
            case "COLABORADOR_JURIDICO":
                context.render("colaboraciones/colaboraciones_persona_juridica.hbs", model);
                break;
            case "COLABORADOR_HUMANO":
                context.render("colaboraciones/colaboraciones_persona_humana.hbs", model);
                break;
            default:
                context.render("Landing-Page.hbs", model);
                break;
        }
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
            model.put("colaboraciones", colaboracionesHistoricas);
            context.render("/colaboraciones/colaboraciones_historico.hbs", model);
        } else {
            context.status(404).result("Colaborador no encontrado");
        }
    }

    public void ColaboracionDinero (Context context){
        Transaccion transaccion = new Transaccion();

        Colaborador colaborador = colaboradorRepositorio.buscarPorIdUsuario(context.sessionAttribute("usuario_id"));

        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre("");
        colaboracion.setTipo("DINERO");
        colaboracion.setDescripcion("");
        colaboracion.setFechaColaboracion(LocalDate.now());
        colaboracion.setTransaccion(transaccion);
        colaboracion.setColaborador(colaborador);


        colaboracionRepositorio.persistir(colaboracion);


        context.redirect("/colaboraciones");
    }

    public void ColaboracionVianda (Context context){
        Transaccion transaccion = new Transaccion();

        Colaborador colaborador = colaboradorRepositorio.buscarPorIdUsuario(context.sessionAttribute("usuario_id"));

        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre("");
        colaboracion.setTipo("DINERO");
        colaboracion.setDescripcion("");
        colaboracion.setFechaColaboracion(LocalDate.now());
        colaboracion.setTransaccion(transaccion);
        colaboracion.setColaborador(colaborador);


        colaboracionRepositorio.persistir(colaboracion);


        context.redirect("/colaboraciones");
    }

    public void ColaboracionDistribucion (Context context){
        Transaccion transaccion = new Transaccion();

        Colaborador colaborador = colaboradorRepositorio.buscarPorIdUsuario(context.sessionAttribute("usuario_id"));

        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre("");
        colaboracion.setTipo("DINERO");
        colaboracion.setDescripcion("");
        colaboracion.setFechaColaboracion(LocalDate.now());
        colaboracion.setTransaccion(transaccion);
        colaboracion.setColaborador(colaborador);


        colaboracionRepositorio.persistir(colaboracion);


        context.redirect("/colaboraciones");
    }

    public void ColaboracionHeladera (Context context){
        Colaborador colaborador = colaboradorRepositorio.buscarPorIdUsuario(context.sessionAttribute("usuario_id"));


        System.out.println("HOLAA");


        Transaccion transaccion = new Transaccion();
        transaccion.setMontoPuntaje(50.0);
        transaccion.setColaborador(colaborador);

        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre("");
        colaboracion.setTipo("");
        colaboracion.setDescripcion("");
        colaboracion.setFechaColaboracion(LocalDate.now());
        colaboracion.setTransaccion(transaccion);
        colaboracion.setColaborador(colaborador);

        Heladera heladera = GenericFactory.createInstance(Heladera.class);

        HostearHeladera hostearHeladera = new HostearHeladera();
        hostearHeladera.setHeladera(heladera);
        hostearHeladera.setColaboracion(colaboracion);
        hostearHeladera.setEnVigencia(true);

        transaccionRepositorio.persistir(hostearHeladera);

        System.out.println("Colaboracion Heladera");

        //context.redirect("/colaboraciones");
    }

    public void PersonaVulnerable (Context context){
        Transaccion transaccion = new Transaccion();

        Colaborador colaborador = colaboradorRepositorio.buscarPorIdUsuario(context.sessionAttribute("usuario_id"));

        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre("");
        colaboracion.setTipo("DISTRIBUIR_TARJETAS");
        colaboracion.setDescripcion("");
        colaboracion.setFechaColaboracion(LocalDate.now());
        colaboracion.setTransaccion(transaccion);
        colaboracion.setColaborador(colaborador);


        colaboracionRepositorio.persistir(colaboracion);


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
