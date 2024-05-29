package dominio.services.recomendadorDePuntos;


import retrofit2.Call;
import retrofit2.http.GET;
import dominio.calculadorDePuntos.ListadoDePuntosRecomendados;
public interface RecomendadorDePuntosAdapter {
    @GET("/api/puntosRecomendados")
    Call<ListadoDePuntosRecomendados> solicitarPuntosRecomendados(Double longitud, double latitud, double radio);
}
