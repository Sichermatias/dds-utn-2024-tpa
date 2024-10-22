package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.HeladerasRepositorio;
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

        // Obtener los datos del formulario
        String tipoIncidenteStr = context.formParam("tipoIncidente");
        String descripcion = context.formParam("descripcion");

        // Convertir el string de tipo de incidente a un enum de TipoIncidente
        TipoIncidente tipoIncidente;
        try {
            tipoIncidente = TipoIncidente.valueOf(tipoIncidenteStr);
        } catch (IllegalArgumentException e) {
            context.status(400).result("Tipo de incidente no válido");
            return;
        }

        // Obtener las fotos (si se suben)
        List<UploadedFile> fotosSubidas = context.uploadedFiles("fotos");
        List<String> fotosIncidente = new ArrayList<>();

        if (fotosSubidas != null && !fotosSubidas.isEmpty()) {
            for (UploadedFile foto : fotosSubidas) {
                // Guardar la foto en el sistema de archivos o en la base de datos y agregarla a la lista
                String fotoPath = guardarFoto(foto); // Método para guardar la foto
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
                LocalDateTime.now(),           // Fecha y hora actual
                heladera,                      // Heladera a la que está asociado el incidente
                tipoIncidente,                 // Tipo de incidente
                colaborador,                   // Colaborador que reporta el incidente
                descripcion,                   // Descripción del incidente
                fotosIncidente                 // Lista de fotos asociadas
        );

        repositorio.persist(nuevoIncidente);

        if (tipoIncidente == TipoIncidente.ALERTA || tipoIncidente == TipoIncidente.FALLA_TECNICA) {
            System.out.print("Entre a el desperfecto");
            heladera.setDesperfecto(true);
            repositorio.actualizar(heladera);
        }

        context.redirect("/heladeras/" + heladeraId);
    }

    public String guardarFoto(UploadedFile foto) {
        try {
            // Definir el directorio donde se almacenarán las fotos
            String directorioFotos = "uploads/fotosIncidentes/";

            // Crear el directorio si no existe
            Path directorioPath = Paths.get(directorioFotos);
            if (!Files.exists(directorioPath)) {
                Files.createDirectories(directorioPath);
            }

            // Obtener el nombre original del archivo
            String nombreOriginal = foto.filename();

            // Generar un nombre único para evitar colisiones (puedes usar un UUID)
            String nombreUnico = UUID.randomUUID().toString() + "_" + nombreOriginal;

            // Crear el path completo donde se guardará el archivo
            Path archivoPath = directorioPath.resolve(nombreUnico);

            // Escribir el archivo en el disco
            Files.write(archivoPath, foto.content().readAllBytes());

            // Devolver la ruta relativa donde se guardó la imagen (para poder usarla en la web)
            return "/" + directorioFotos + nombreUnico;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar la foto del incidente.");
        }
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

        if (tipoRol != null) {
            model.put("heladera", heladera);
            context.render("/heladeras/heladera_ind.hbs", model);
        }
        else context.redirect("/login");
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
