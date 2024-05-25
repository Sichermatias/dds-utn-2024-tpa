package dominio.CalculadorPuntos;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import java.io.IOException;

public class RecomendadorDePuntos{
    private static RecomendadorDePuntos recomendadorDePuntos = null;
    private static final String urlApi = "";
    private Retrofit retrofit;

    private RecomendadorDePuntos() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static RecomendadorDePuntos getRecomendadorDePuntos() {
        if(recomendadorDePuntos == null){
            recomendadorDePuntos = new RecomendadorDePuntos();
        }
        return recomendadorDePuntos;
    }

    public ListadoDePuntosRecomendados listadoDePuntosRecomendados(Double longitudPunto, Double latitudPunto, Double radio) throws IOException {
        RecomendadorDePuntosAdapter recomendadorDePuntosAdapter = this.retrofit.create(RecomendadorDePuntosAdapter.class);
        Call<ListadoDePuntosRecomendados> solicitarPuntosRecomendados = recomendadorDePuntosAdapter.solicitarPuntosRecomendados(longitudPunto, latitudPunto, radio);
        Response<ListadoDePuntosRecomendados> responsePuntosRecomendados= solicitarPuntosRecomendados.execute();
        return responsePuntosRecomendados.body();
    }
}
