package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dominio.colaboracion.Premio;
import ar.edu.utn.frba.dds.dominio.colaboracion.PremioCanje;
import ar.edu.utn.frba.dds.dominio.colaboracion.Transaccion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboradorRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.PremioCanjeRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.PremioRepositorio;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.server.exceptions.FaltaDeStockException;
import ar.edu.utn.frba.dds.server.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PremioCanjesController extends Controller implements ICrudViewsHandler {
    private final ColaboradorRepositorio colaboradorRepositorio;
    private final PremioRepositorio premioRepositorio;
    private final PremioCanjeRepositorio premioCanjeRepositorio;

    public PremioCanjesController(ColaboradorRepositorio colaboradorRepositorio, PremioRepositorio premioRepositorio, PremioCanjeRepositorio premioCanjeRepositorio) {
        this.colaboradorRepositorio = colaboradorRepositorio;
        this.premioRepositorio = premioRepositorio;
        this.premioCanjeRepositorio = premioCanjeRepositorio;
    }

    @Override
    public void index(Context context) {
        Usuario usuario = this.usuarioLogueado(context);
        Map<String, Object> model = new HashMap<>();
        Long usuarioId = context.sessionAttribute("usuario_id");
        String tipoRol = context.sessionAttribute("tipo_rol");
        if (usuarioId != null && tipoRol != null) {
            List<PremioCanje> premioCanjes;
            if(tipoRol.equals("ADMIN")) {
                premioCanjes = this.premioCanjeRepositorio.buscarTodos(PremioCanje.class);
            } else if(tipoRol.equals("COLABORADOR_HUMANO") || tipoRol.equals("COLABORADOR_JURIDICO")){
                Colaborador colaborador = this.colaboradorRepositorio.buscarPorIdUsuario(usuario.getId());
                premioCanjes = this.premioCanjeRepositorio.buscarPorColaboradorId(PremioCanje.class, colaborador.getId());
            } else {
                throw new AccessDeniedException();
            }
            model.put("usuario_id", usuarioId);
            model.put("tipo_rol", tipoRol);
            model.put("premioCanjes", premioCanjes);

            context.render("puntosYPremios/mis_premios.hbs", model);
        } else
            context.redirect("/login?return=puntos-y-premios/premios-canjeados");
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) throws IOException {
        try {
            Usuario usuario = this.usuarioLogueado(context);
            if (usuario != null) {
                LocalDateTime fechaHoraActual = LocalDateTime.now();

                Colaborador colaborador = this.colaboradorRepositorio.buscarPorIdUsuario(usuario.getId());

                long premioId = Long.parseLong(Objects.requireNonNull(context.formParam("premioId")));
                System.out.println(premioId);
                Premio premio = this.premioRepositorio.buscarPorId(Premio.class, premioId);

                if(premio.getCantidadDisponible() <= 0)
                    throw new FaltaDeStockException();

                if(colaborador.getPuntaje() < premio.getCantidadPuntosNecesarios())
                    throw new PuntosInsuficientesException();

                Double valorTransaccion = premio.getCantidadPuntosNecesarios() * -1;

                premio.restarleAlStock(1);

                Transaccion transaccion = new Transaccion();
                transaccion.setFechaHoraAlta(fechaHoraActual);
                transaccion.setColaborador(colaborador);
                transaccion.setMontoPuntaje(valorTransaccion);

                colaborador.actualizarPuntajeSumandole(valorTransaccion);

                PremioCanje premioCanje = new PremioCanje();
                premioCanje.setFechaHoraAlta(fechaHoraActual);
                premioCanje.setPremio(premio);
                premioCanje.setTransaccion(transaccion);
                premioCanje.setColaborador(colaborador);

                this.premioCanjeRepositorio.persistir(premioCanje);

                context.redirect("/puntos-y-premios?canjeado=" + true);
            } else
                context.redirect("/login?return=puntos-y-premios");
        } catch (PuntosInsuficientesException e) {
            context.redirect("/puntos-y-premios?puntos-insuficientes=" + true);
        } catch (FaltaDeStockException e) {
            context.redirect("/puntos-y-premios?falta-de-stock=" + true);
        } catch (Exception e) {
            context.redirect("/puntos-y-premios?error=" + true);
        }


    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {
        String tipoRol = context.sessionAttribute("tipo_rol");
        if(tipoRol == null || !tipoRol.equals("ADMIN"))
            throw new AccessDeniedException();

        long premioCanjeID = Long.parseLong(context.pathParam("premioCanjeID"));
        PremioCanje premioCanje = this.premioCanjeRepositorio.buscarPorId(PremioCanje.class, premioCanjeID);
        premioCanje.setEntregado(true);
        this.premioCanjeRepositorio.actualizar(premioCanje);
        context.redirect("/puntos-y-premios/premios-canjeados");
    }

    @Override
    public void delete(Context context) {

    }
}
