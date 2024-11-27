package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.NombreDeMedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.VisitaIncidente;
import ar.edu.utn.frba.dds.dominio.infraestructura.Heladera;
import ar.edu.utn.frba.dds.dominio.persona.Tecnico;
import ar.edu.utn.frba.dds.dominio.persona.TipoDocumento;
import ar.edu.utn.frba.dds.dominio.persona.login.Rol;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import net.bytebuddy.asm.Advice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class TecnicosController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private final ColaboradorRepositorio personaRepositorio;
    private final LocalidadRepositorio localidadRepositorio;

    public TecnicosController(ColaboradorRepositorio personaRepositorio, LocalidadRepositorio localidadRepositorio) {
        this.personaRepositorio = personaRepositorio;
        this.localidadRepositorio = localidadRepositorio;
    }
    public void formularioTecnico(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        List<Localidad> localidades= localidadRepositorio.buscarTodos(Localidad.class);
        if(tipoRol!= null){
            model.put("localidades", localidades);
            context.render("/registro/Registro-Tecnico.hbs", model);
        }
        else context.redirect("/login");
    }
    public void registrarTecnico(Context context) {
        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String tipoDocumento = context.formParam("tipoDocumento");
        String nroDocumento = context.formParam("nroDocumento");
        String cuil = context.formParam("cuil");
        String telegramID = context.formParam("telegramID");

        List<String> mediosDeContacto = context.formParams("medioDeContacto[]");
        List<String> datosDeContacto = context.formParams("datoContacto[]");

        List<String> localidadesDeServicio = context.formParams("localidadesDeServicio");

        Tecnico tecnico = new Tecnico();
        tecnico.setNombre(nombre);
        tecnico.setApellido(apellido);
        tecnico.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
        tecnico.setNroDocumento(nroDocumento);
        tecnico.setCuil(cuil);
        tecnico.setTelegramID(telegramID);

        // Fecha de alta del técnico
        tecnico.setFechaHoraAlta(LocalDateTime.now());

        NombreDeMedioDeContacto nombreMedio = new NombreDeMedioDeContacto(mediosDeContacto.get(0));
        MedioDeContacto medioDeContacto = new MedioDeContacto(nombreMedio, datosDeContacto.get(0));
        tecnico.agregarMedioDeContacto(medioDeContacto);

        // Asociar las localidades de servicio
        for (String localidad : localidadesDeServicio) {
            Localidad localidadServicio = localidadRepositorio.buscarPorNombre(Localidad.class, localidad).get(0);
            if (localidadServicio != null) {
                tecnico.agregarLocalidadServicio(localidadServicio);
            }
        }

        Usuario usuario = new Usuario();

        List<Rol> roles = personaRepositorio.buscarPorRol(Rol.class, "Tecnico");
        usuario.setRol(roles.get(0));

        tecnico.setUsuario(usuario);

        usuario.setNombreUsuario(nombre+apellido);
        usuario.setContrasenia(cuil );

        personaRepositorio.persistir(tecnico);

        context.status(HttpStatus.CREATED).redirect("/login");
    }
    public Tecnico obtenerTecnicoPorUsuarioId(Long usuarioId) {
        List<Tecnico> colaboradores = personaRepositorio.buscarPorUsuarioId(Tecnico.class,usuarioId);
        return colaboradores.isEmpty() ? null : colaboradores.get(0);
    }

    public void listarIncidentes(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        if(tipoRol==null){context.redirect("/login");};

        Tecnico tecnico=obtenerTecnicoPorUsuarioId(usuarioId);
        List<Incidente> incidentesAsignados = tecnico.getIncidentesAsignados();


        if (tipoRol != null) {
            model.put("incidentes", incidentesAsignados);
            context.render("/tecnicos/incidentes.hbs", model);
        }
        else context.redirect("/login");
    }

    public void indexIncidente(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        Long heladeraId= Long.valueOf(context.pathParam("id"));
        model.put("tipo_rol", tipoRol);
        model.put("usuario_id", usuarioId);

        Tecnico tecnico = obtenerTecnicoPorUsuarioId(usuarioId);
        List<Incidente> incidentesAsignados=tecnico.getIncidentesAsignados();
        List<Incidente> incidentesFiltrados= incidentesAsignados.stream().filter(incidente -> incidente.getHeladeraIncidente().getId()==heladeraId
        && !incidente.getResuelto()).toList();
        Incidente incidente=incidentesFiltrados.get(0);

        HeladerasRepositorio repositorio= HeladerasRepositorio.getInstancia();
            Heladera heladera = repositorio.buscarPorId(Heladera.class, heladeraId);
            if (tipoRol != null) {
                model.put("heladera", heladera);
                model.put("incidente", incidente);
                context.render("/tecnicos/incidente_heladera.hbs", model);
            }
            else context.redirect("/login");
    }
    public void cerrarIncidente(Context context){
        VisitaTecnicaRepositorio visitaTecnicaRepositorio= new VisitaTecnicaRepositorio();
        IncidenteRepositorio incidenteRepositorio=new IncidenteRepositorio();

        Long usuarioId= context.sessionAttribute("usuario_id");
        Long incidenteId = Long.valueOf(context.formParam("incidenteId"));

        Incidente incidente=incidenteRepositorio.buscarPorId(Incidente.class,incidenteId);

        String fechaHoraVisitaStr = context.formParam("fechaVisita");
        LocalDateTime fechaHoraVisita = LocalDateTime.parse(fechaHoraVisitaStr);

        // Recuperar el estado del incidente (si está resuelto o no)
        Integer resuelto = Integer.valueOf(context.formParam("incidenteResuelto"));

        List<UploadedFile> fotosSubidas = context.uploadedFiles("fotosVisita");
        ArrayList<String> fotosIncidente = new ArrayList<>();
        if (fotosSubidas != null && !fotosSubidas.isEmpty()) {
            for (UploadedFile foto : fotosSubidas) {
                String fotoPath = guardarFoto(foto);
                fotosIncidente.add(fotoPath);
            }
        }
        Boolean resueltoEnserio=false;
        if (resuelto==1){
            resueltoEnserio=true;
            incidente.getHeladeraIncidente().setDesperfecto(false);
            incidente.setResuelto(true);
        }
        VisitaIncidente visitaIncidente=new VisitaIncidente(
                obtenerTecnicoPorUsuarioId(usuarioId),
                incidente,
                fechaHoraVisita,
                resueltoEnserio

        );
        visitaIncidente.setFechaHoraAlta(LocalDateTime.now());
        visitaIncidente.agregarFotos(fotosIncidente);
        visitaIncidente.getTecnicoAsignado().removerIncidente(incidente);
        visitaTecnicaRepositorio.persistir(visitaIncidente);

        context.redirect("/");
    }
    public String guardarFoto(UploadedFile foto) {
        try {
            // Definir el directorio donde se almacenarán las fotos
            String directorioFotos = "src/main/resources/uploads/incidentes/tecnico/";

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
            throw new RuntimeException("Error al guardar la foto del tecnico.");
        }
    }
    @Override
    public void index(Context context) {
    }

    @Override
    public void show(Context context) {
    }

    @Override
    public void create(Context context) {
    }

    @Override
    public void save(Context context) throws IOException {
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
