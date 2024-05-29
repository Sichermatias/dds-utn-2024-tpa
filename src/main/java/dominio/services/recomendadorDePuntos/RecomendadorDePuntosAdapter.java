package dominio.services.recomendadorDePuntos;


import retrofit2.Call;
import retrofit2.http.GET;
import dominio.calculadorDePuntos.ListadoDePuntosRecomendados;
import retrofit2.http.Query;

public interface RecomendadorDePuntosAdapter {
    @GET("recomendadorDePuntos")
    Call<ListadoDePuntosRecomendados> solicitarPuntosRecomendados(@Query("longitud") Double longitud, @Query("latitud") Double latitud, @Query("radio") Double radio);
}
