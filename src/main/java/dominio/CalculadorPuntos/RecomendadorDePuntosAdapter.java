package dominio.CalculadorPuntos;


import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import dominio.CalculadorPuntos.ListadoDePuntosRecomendados;
public interface RecomendadorDePuntosAdapter {
    @GET("/api/puntosRecomendados")
    Call<ListadoDePuntosRecomendados> solicitarPuntosRecomendados(Double longitud, double latitud, double radio);
}
