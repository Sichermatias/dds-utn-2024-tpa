package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.archivos.carga_masiva.CampoInvalidoException;
import ar.edu.utn.frba.dds.dominio.archivos.carga_masiva.CargaMasiva;
import ar.edu.utn.frba.dds.dominio.archivos.carga_masiva.ProcesadorCampos;
import ar.edu.utn.frba.dds.dominio.archivos.carga_masiva.SplitterLineas;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensajero;
import ar.edu.utn.frba.dds.dominio.services.messageSender.strategies.EstrategiaMensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.strategies.EstrategiaWhatsapp;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CargaMasivaController implements ICrudViewsHandler, WithSimplePersistenceUnit {
    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        String usuarioId= context.sessionAttribute("usuario_id");
        System.out.print(tipoRol);
        System.out.print(usuarioId);
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
        }
        context.render("Carga-Masiva.hbs", model);
    }
    public void cargarCSV(Context ctx) {
        UploadedFile file = ctx.uploadedFile("csvFile");
        if (file != null) {
            try (InputStream inputStream = file.content();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String linea;
                EstrategiaMensaje estrategiaMensaje= new EstrategiaWhatsapp();
                Mensajero mensajero = new Mensajero(estrategiaMensaje);
                CargaMasiva cargaMasiva = new CargaMasiva(mensajero);
                while ((linea = reader.readLine()) != null) {
                    String[] campos = SplitterLineas.split_linea(linea, ",");
                    Colaborador colaborador = ProcesadorCampos.procesarCampos(campos);
                    cargaMasiva.cargarPersona(colaborador);
                }
                ctx.redirect("/");
                } catch (CampoInvalidoException e) {
                    ctx.status(400).result("Error en el archivo: " + e.getMessage());
                } catch (Exception e) {
                    ctx.status(500).result("Error al procesar el archivo.");
                }
            } else {
                ctx.status(400).result("No se cargó ningún archivo.");
            }
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

