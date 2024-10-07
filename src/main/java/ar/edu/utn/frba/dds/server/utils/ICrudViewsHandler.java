package ar.edu.utn.frba.dds.server.utils;
import io.javalin.http.Context;

public interface ICrudViewsHandler {
    void index(Context context);
    void show(Context context);
    void create(Context context);
    void save(Context context);
    void edit(Context context);
    void update(Context context);
    void delete(Context context);

    /*
    default boolean esAdmin(Context contexto){
        if(contexto.sessionAttribute("usuario_id")!=null){
            long id = contexto.sessionAttribute("usuario_id");
            UsuarioRepositorio usuarioRepositorio = UsuarioRepositorio.getInstancia();
            Usuario usuario = usuarioRepositorio.buscarPorId(Usuario.class,id);
            return usuario.getRol().equals(Rol.ADMIN);
        }
       return false;

    }
*/

}
