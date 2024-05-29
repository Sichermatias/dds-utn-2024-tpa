package dominio.services.recomendadorDePuntos;
import dominio.calculadorDePuntos.ListadoDePuntosRecomendados;
import dominio.calculadorDePuntos.PuntoRecomendado;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

import java.io.IOException;

public class RecomendadorDePuntos{
    private static RecomendadorDePuntos recomendadorDePuntos = null;
    private static final String urlApi = "https://b170dbba-4966-4545-8e61-9f5f33ce8569.mock.pstmn.io/api/";
    private Retrofit retrofit;

    private RecomendadorDePuntos() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static RecomendadorDePuntos recomendadorDePuntos() {
        if(recomendadorDePuntos == null){
            recomendadorDePuntos = new RecomendadorDePuntos();
        }
        return recomendadorDePuntos;
    }

    public ListadoDePuntosRecomendados listadoDePuntosRecomendados(Double longitud, Double latitud, Double radio) throws IOException {
        RecomendadorDePuntosAdapter recomendadorDePuntosAdapter = this.retrofit.create(RecomendadorDePuntosAdapter.class);
        Call<ListadoDePuntosRecomendados> solicitarPuntosRecomendados = recomendadorDePuntosAdapter.solicitarPuntosRecomendados(longitud, latitud, radio);
        Response<ListadoDePuntosRecomendados> responsePuntosRecomendados= solicitarPuntosRecomendados.execute();
        return responsePuntosRecomendados.body();
    }
}
