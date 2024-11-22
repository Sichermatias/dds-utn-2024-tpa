package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.PremioRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
        Long usuarioId = context.sessionAttribute("usuario_id");
        String tipoRol = context.sessionAttribute("tipo_rol");
        if (usuarioId != null) {
            Boolean canjeado = Boolean.valueOf(context.queryParam("canjeado"));
            Boolean error = Boolean.valueOf(context.queryParam("error"));

            Colaborador colaborador = this.colaboradorRepositorio.buscarPorIdUsuario(usuario.getId());
            model.put("usuario_id", usuarioId);
            model.put("tipo_rol", tipoRol);
            model.put("puntaje", colaborador.getPuntaje());

            List<Premio> premios = this.premioRepositorio.buscarTodos(Premio.class);
            model.put("premios", premios);

            List<String> rubros = this.premioRepositorio.buscarTodosLosRubros();
            model.put("rubros", rubros);

            model.put("canjeado", canjeado);
            model.put("error", error);

            context.render("puntos_y_premios.hbs", model);
        } else
            context.redirect("/login?return=puntos-y-premios");
    }

    @Override
    public void show(Context context) {
    }
    @Override
    public void create(Context context) {

    }
    @Override
    public void save(Context context) throws IOException {
        Usuario usuario = this.usuarioLogueado(context);
        Colaborador colaborador = this.colaboradorRepositorio.buscarPorIdUsuario(usuario.getId());

        String nombre = context.formParam("nombre");
        List<RubroPremio> rubroPremio = this.premioRepositorio
                .buscarRubroPorNombre(context.formParam("rubro"));
        Double cantidadPuntosNecesarios = Double.valueOf(Objects.requireNonNull(context.formParam("cantPuntosNecesarios")));
        Integer cantidadDonada = Integer.valueOf(Objects.requireNonNull(context.formParam("cantDonada")));
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        UploadedFile fotoSubida = context.uploadedFile("imagenPremio");
        String fotoPath="";
        if (fotoSubida != null) {
            fotoPath = saveImage(fotoSubida);
        }

        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombre("Donacion de premio: " + nombre + ", " + cantidadDonada + " unidad/es.");
        colaboracion.setTipo("PREMIO");
        colaboracion.setFechaColaboracion(LocalDate.from(fechaHoraActual));
        colaboracion.setFechaHoraAlta(fechaHoraActual);
        colaboracion.setColaborador(colaborador);

        Premio premio = new Premio();
        premio.setNombre(nombre);
        if(!rubroPremio.isEmpty()){
            premio.setRubro(rubroPremio.get(0));
        }else{
            RubroPremio rubro=new RubroPremio();
            rubro.setNombre(context.formParam("rubro"));
            rubro.setFechaHoraAlta(LocalDateTime.now());
            premio.setRubro(rubro);
        }
        premio.setImagenPremio(fotoPath);
        premio.setCantidadDisponible(cantidadDonada);
        premio.setCantidadPuntosNecesarios(cantidadPuntosNecesarios);
        premio.setFechaHoraAlta(fechaHoraActual);
        premio.setActivo(true);

        OfrecerPremio ofrecerPremio = new OfrecerPremio();
        ofrecerPremio.setPremio(premio);
        ofrecerPremio.setCantidad(cantidadDonada);
        ofrecerPremio.setFechaHoraAlta(fechaHoraActual);
        ofrecerPremio.setActivo(true);

        Transaccion transaccion=new Transaccion();
        transaccion.setMontoPuntaje(ofrecerPremio.puntaje());
        transaccion.setColaborador(colaborador);
        transaccion.setFechaHoraAlta(LocalDateTime.now());

        colaboracion.setTransaccion(transaccion);
        ofrecerPremio.setColaboracion(colaboracion);

        this.colaboracionRepositorio.persistir(ofrecerPremio);

        context.redirect("/colaboraciones/premio?enviado=" + true);
    }

    public String saveImage(UploadedFile foto) {
        try {
            // Definir el directorio donde se almacenarán las fotos
            String directorioFotos = "src/main/resources/uploads/premios/";

            Path directorioPath = Paths.get(directorioFotos);
            if (!Files.exists(directorioPath)) {
                Files.createDirectories(directorioPath);
            }

            String nombreOriginal = foto.filename();

            String nombreUnico = UUID.randomUUID().toString() + "_" + nombreOriginal;

            Path archivoPath = directorioPath.resolve(nombreUnico);

            Files.write(archivoPath, foto.content().readAllBytes());

            return "/uploads/premios/" + nombreUnico;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar la foto del premio.");
        }
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
