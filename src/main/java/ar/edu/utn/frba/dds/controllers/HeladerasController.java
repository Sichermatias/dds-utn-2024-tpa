package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.dominio.infraestructura.FiltroSuscripcion;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.infraestructura.Suscripcion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.services.messageSender.strategies.EstrategiaMail;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.services.GestorDeIncidentesService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class HeladerasController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    ColaboradorRepositorio colaboradorRepositorio= ColaboradorRepositorio.getInstancia();

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        List<Heladera> heladeras = repositorio.buscarTodas();
        if (tipoRol != null) {
            model.put("heladeras", heladeras);
            context.render("/heladeras/heladeras.hbs", model);
        }
        else context.redirect("/login");
    }

    public void indexFalla(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        Long heladeraId= Long.valueOf(context.pathParam("id"));
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        Heladera heladera = (Heladera) repositorio.buscarPorId(Heladera.class, heladeraId);

        if (tipoRol != null) {
            model.put("heladera", heladera);
            context.render("/heladeras/heladera_falla.hbs", model);
        }
        else context.redirect("/login");
    }
    public void formularioIncidente(Context context) {

        Long usuarioId= context.sessionAttribute("usuario_id");

        Long heladeraId = Long.valueOf(context.pathParam("id"));

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        Heladera heladera = (Heladera) repositorio.buscarPorId(Heladera.class, heladeraId);

        if (heladera == null) {
            context.status(404).result("Heladera no encontrada");
            return;
        }

        String tipoIncidenteStr = context.formParam("tipoIncidente");
        String descripcion = context.formParam("descripcion");

        TipoIncidente tipoIncidente;
        try {
            tipoIncidente = TipoIncidente.valueOf(tipoIncidenteStr);
        } catch (IllegalArgumentException e) {
            context.status(400).result("Tipo de incidente no válido");
            return;
        }

        List<UploadedFile> fotosSubidas = context.uploadedFiles("imagenPremio");
        List<String> fotosIncidente = new ArrayList<>();
        if (fotosSubidas != null && !fotosSubidas.isEmpty()) {
            for (UploadedFile foto : fotosSubidas) {
                String fotoPath = guardarFoto(foto);
                fotosIncidente.add(fotoPath);
            }
        }

        ColaboradorRepositorio colaboradorRepositorio=ColaboradorRepositorio.getInstancia();
        Colaborador colaborador = colaboradorRepositorio.obtenerColaboradorPorUsuarioId(usuarioId);
        if (colaborador == null) {
            context.status(401).result("Debe estar autenticado para reportar un incidente");
            return;
        }

        Incidente nuevoIncidente = new Incidente(
                LocalDateTime.now(),
                heladera,
                tipoIncidente,
                colaborador,
                descripcion,
                fotosIncidente
        );
        nuevoIncidente.setAsignado(false);
        heladera.setCantSemanalIncidentes(heladera.getCantSemanalIncidentes()+1);
        ColaboracionRepositorio repo=ColaboracionRepositorio.getInstancia();
        repo.persistir(nuevoIncidente);
        if (tipoIncidente == TipoIncidente.ALERTA_FALLA_CONEXION ||
                tipoIncidente == TipoIncidente.ALERTA_FRAUDE ||
                tipoIncidente == TipoIncidente.ALERTA_TEMPERATURA ||
                tipoIncidente == TipoIncidente.FALLA_TECNICA) {
            heladera.setDesperfecto(true);
            repositorio.actualizar(heladera);
        }
        GestorDeIncidentesService gestorDeIncidentesService = ServiceLocator.instanceOf(GestorDeIncidentesService.class);
        gestorDeIncidentesService.gestionarIncidente(nuevoIncidente);

        context.redirect("/heladeras/" + heladeraId);
    }

    public String guardarFoto(UploadedFile foto) {
        try {
            // Definir el directorio donde se almacenarán las fotos
            String directorioFotos = "src/main/resources/uploads/incidentes/";

            Path directorioPath = Paths.get(directorioFotos);
            if (!Files.exists(directorioPath)) {
                Files.createDirectories(directorioPath);
            }

            String nombreOriginal = foto.filename();

            String nombreUnico = UUID.randomUUID().toString() + "_" + nombreOriginal;

            Path archivoPath = directorioPath.resolve(nombreUnico);

            Files.write(archivoPath, foto.content().readAllBytes());

            return "/" + directorioFotos + nombreUnico;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar la foto del incidente.");
        }
    }

    public void indexSuscripcionHeladera(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        Long heladeraId= Long.valueOf(context.pathParam("id"));
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        Heladera heladera = (Heladera) repositorio.buscarPorId(Heladera.class, heladeraId);
        Colaborador colaborador= colaboradorRepositorio.obtenerColaboradorPorUsuarioId(usuarioId);
        if (tipoRol != null) {
            model.put("colaborador", colaborador);
            model.put("heladera", heladera);
            context.render("/heladeras/heladera_suscripcion.hbs", model);
        }
        else context.redirect("/login");

    }

    public void suscripcionHeladera(Context context){
        Long usuarioId= context.sessionAttribute("usuario_id");
        Long heladeraId = Long.valueOf(context.pathParam("id"));

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        Heladera heladera = (Heladera) repositorio.buscarPorId(Heladera.class, heladeraId);

        ColaboradorRepositorio colaboradorRepositorio=ColaboradorRepositorio.getInstancia();
        Colaborador colaborador = colaboradorRepositorio.obtenerColaboradorPorUsuarioId(usuarioId);
        Integer viandasParaLlenar = context.formParamAsClass("viandasParaLlenar", Integer.class).getOrDefault(null);
        Integer minViandas = context.formParamAsClass("minViandas", Integer.class).getOrDefault(null);
        Boolean desperfecto = context.formParamAsClass("desperfecto", Boolean.class).getOrDefault(null);
        String contacto = context.formParam("contacto");

        FiltroSuscripcion filtro = new FiltroSuscripcion(viandasParaLlenar, minViandas, desperfecto);

        Mensajero mensajero =new Mensajero(new EstrategiaMail());

        Suscripcion nuevaSuscripcion = colaborador.suscribirseHeladera(heladera,filtro,mensajero,contacto);

        heladera.agregarSuscripcion(nuevaSuscripcion);

        repositorio.persist(nuevaSuscripcion);
        repositorio.actualizar(heladera);
        colaboradorRepositorio.actualizar(colaborador);

        context.redirect("/heladeras/" + heladeraId);
    }
    public void indexInd(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        Long heladeraId= Long.valueOf(context.pathParam("id"));
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        Heladera heladera = (Heladera) repositorio.buscarPorId(Heladera.class, heladeraId);

        model.put("verIncidentes", false);

        if (Objects.equals(tipoRol, "ADMIN")){
            model.put("verIncidentes", true);
        } else if (Objects.equals(tipoRol, null)) {
            context.redirect("/login");
        } else {
            Colaborador colaborador = colaboradorRepositorio.obtenerColaboradorPorUsuarioId(usuarioId);
            if (colaborador.estaSuscrito(heladera)){
                model.put("yaSuscrito", true);
            }
            if (colaborador.hostea(heladera)){
                model.put("verIncidentes", true);
            }
        }

        model.put("heladera", heladera);
        context.render("/heladeras/heladera_ind.hbs", model);

    }


    public void indexMiSuscripcionHeladera(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        Long heladeraId= Long.valueOf(context.pathParam("id"));
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        Heladera heladera = (Heladera) repositorio.buscarPorId(Heladera.class, heladeraId);
        Colaborador colaborador= colaboradorRepositorio.obtenerColaboradorPorUsuarioId(usuarioId);
        Suscripcion suscripcion=colaborador.queSuscripcion(heladera);
        if (tipoRol != null) {
            model.put("suscripcion", suscripcion);
            model.put("heladera", heladera);
            context.render("/heladeras/heladera_misuscripcion.hbs", model);
        }
        else context.redirect("/login");
    }

    public void miSuscripcionHeladera(Context context){
        Long usuarioId= context.sessionAttribute("usuario_id");
        Long heladeraId = Long.valueOf(context.pathParam("id"));

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        Heladera heladera = (Heladera) repositorio.buscarPorId(Heladera.class, heladeraId);

        ColaboradorRepositorio colaboradorRepositorio=ColaboradorRepositorio.getInstancia();
        Colaborador colaborador = colaboradorRepositorio.obtenerColaboradorPorUsuarioId(usuarioId);
        Integer viandasParaLlenar = context.formParamAsClass("viandasParaLlenar", Integer.class).getOrDefault(null);
        Integer minViandas = context.formParamAsClass("minViandas", Integer.class).getOrDefault(null);
        Boolean desperfecto = context.formParamAsClass("desperfecto", Boolean.class).getOrDefault(null);
        String contacto = context.formParam("contacto");

        FiltroSuscripcion filtro = new FiltroSuscripcion(viandasParaLlenar, minViandas, desperfecto);

        Mensajero mensajero =new Mensajero(new EstrategiaMail());

        Suscripcion nuevaSuscripcion = colaborador.suscribirseHeladera(heladera,filtro,mensajero,contacto);

        heladera.agregarSuscripcion(nuevaSuscripcion);

        repositorio.persist(nuevaSuscripcion);
        repositorio.actualizar(heladera);
        colaboradorRepositorio.actualizar(colaborador);

        context.redirect("/heladeras/" + heladeraId);
    }

    public void darDeBajaSuscripcion(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        Long heladeraId = Long.valueOf(context.pathParam("id"));
        if(usuarioId!=null){
        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
        Heladera heladera =repositorio.buscarPorId(Heladera.class, heladeraId);
        Colaborador colaborador = colaboradorRepositorio.obtenerColaboradorPorUsuarioId(usuarioId);
        colaborador.cancelarSuscripcion(heladera);
        colaboradorRepositorio.actualizar(colaborador);
        }
        context.redirect("/heladeras");
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
