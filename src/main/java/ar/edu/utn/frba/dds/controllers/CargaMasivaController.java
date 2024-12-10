package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.archivos.carga_masiva.CampoInvalidoException;
import ar.edu.utn.frba.dds.dominio.archivos.carga_masiva.CargaMasiva;
import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.TipoRol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.services.messageSender.strategies.EstrategiaMail;
import ar.edu.utn.frba.dds.dominio.services.messageSender.strategies.EstrategiaMensaje;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.DonacionDineroRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.TransaccionRepositorio;
import ar.edu.utn.frba.dds.services.ColaboracionService;
import ar.edu.utn.frba.dds.services.TransaccionService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargaMasivaController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
            context.render("Carga-Masiva.hbs", model);
        }else context.redirect("/login");
    }
    public void cargarCSV(Context ctx) {
        UploadedFile file = ctx.uploadedFile("csvFile");
        if (file != null) {
            try (InputStream inputStream = file.content();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                EstrategiaMensaje estrategiaMensaje = new EstrategiaMail(); // Configura el mensajero
                Mensajero mensajero = new Mensajero(estrategiaMensaje);
                CargaMasiva cargaMasiva = new CargaMasiva(mensajero);

                String rutaTemporal = guardarArchivoTemporal(file);
                cargaMasiva.cargarArchivo(rutaTemporal, ";");            ColaboradorRepositorio repositorio = ColaboradorRepositorio.getInstancia(); // Repositorio usado en varias ocasiones
                ctx.redirect("/");

            } catch (CampoInvalidoException e) {
                ctx.status(400).result("Error en el archivo: " + e.getMessage());
            } catch (IOException e) {
                ctx.status(500).result("Error de entrada/salida al procesar el archivo.");
            } catch (Exception e) {
                ctx.status(500).result("Error inesperado al procesar el archivo.");
            }
        } else {
            ctx.status(400).result("No se cargó ningún archivo.");
        }
    }

    private String guardarArchivoTemporal(UploadedFile file) throws IOException {
        File tempFile = File.createTempFile("cargaMasiva-", ".csv");
        try (InputStream inputStream = file.content();
             OutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return tempFile.getAbsolutePath();
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

