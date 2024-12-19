package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.dominio.persona.*;
import ar.edu.utn.frba.dds.models.repositories.imp.PersonaVulnerableRepositorio;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegistroVulnerableController implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private final PersonaVulnerableRepositorio personaVulnerableRepositorio;

    private final ColaboracionController colaboracionController;

    public RegistroVulnerableController(PersonaVulnerableRepositorio personaVulnerableRepositorio, ColaboracionController colaboracionController) {
        this.personaVulnerableRepositorio = personaVulnerableRepositorio;
        this.colaboracionController = colaboracionController;
    }

    public void indexRegistroVulnerable(Context context){
        Map<String, Object> model = new HashMap<>();
        String tipoRol = context.sessionAttribute("tipo_rol");
        Long usuarioId= context.sessionAttribute("usuario_id");
        if (tipoRol != null) {
            model.put("tipo_rol", tipoRol);
            model.put("usuario_id", usuarioId);
        context.render("registro/Registro-Vulnerable.hbs", model);}
        else context.redirect("/login");
    }
    public void registroVulnerable(Context context){

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String tipoDocumento = context.formParam("tipoDocumento");
        String nroDocumento = context.formParam("nroDocumento");
        String fechaNacimiento = context.formParam("fechaNacimiento");

        List<String> nombrePersonaACargo = context.formParams("nombrePersonaACargo[]");
        List<String> apellidoPersonaACargo = context.formParams("apellidoPersonaACargo[]");
        List<String> tipoDocumentoACargo = context.formParams("tipoDocumento[]");
        List<String> nroDocumentoACargo = context.formParams("nroDocumento[]");
        List<String> fechaNacimientoACargo = context.formParams("fechaNacimiento[]");

        String cantUsosMaximosPorDia = context.formParam("cantUsosMaximosPorDia");

        //Crear el objeto PersonaVulnerable
        PersonaVulnerable personaVulnerable = new PersonaVulnerable();
        personaVulnerable.setNombre(nombre);
        personaVulnerable.setApellido(apellido);
        personaVulnerable.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
        personaVulnerable.setNroDocumento(nroDocumento);
        personaVulnerable.setFechaNacimiento(LocalDate.parse(fechaNacimiento));

        personaVulnerable.setCantUsosMaximosPorDia(Integer.valueOf(cantUsosMaximosPorDia));

        if (!Objects.equals(nombrePersonaACargo.get(0), "")){
            // Agregar personas a cargo
            for (int i = 0; i < nombrePersonaACargo.size(); i++) {
                PersonaVulnerable personaVulnerableACargo = new PersonaVulnerable();
                personaVulnerableACargo.setNombre(nombrePersonaACargo.get(i));
                personaVulnerableACargo.setApellido(apellidoPersonaACargo.get(i));
                personaVulnerableACargo.setTipoDocumento(TipoDocumento.valueOf(tipoDocumentoACargo.get(i)));
                personaVulnerableACargo.setNroDocumento(nroDocumentoACargo.get(i));
                personaVulnerableACargo.setFechaNacimiento(LocalDate.parse(fechaNacimientoACargo.get(i)));

                personaVulnerable.agregarPersonasVulnerablesACargo(personaVulnerableACargo);
            }
        }

        this.colaboracionController.colaboracionTarjetas(context,personaVulnerable);

        context.redirect("/");
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