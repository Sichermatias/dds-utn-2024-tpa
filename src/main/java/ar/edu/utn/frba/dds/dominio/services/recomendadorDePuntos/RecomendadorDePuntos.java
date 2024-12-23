package ar.edu.utn.frba.dds.dominio.services.recomendadorDePuntos;
import ar.edu.utn.frba.dds.dominio.calculadorDePuntos.ListadoDePuntosRecomendados;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

import java.io.IOException;

public class RecomendadorDePuntos{
    private static RecomendadorDePuntos recomendadorDePuntos = null;
    private static final String urlApi = "https://aefbd88d-6d87-465a-83ee-316af948a4c1.mock.pstmn.io/api/";
    private Retrofit retrofit;

    public RecomendadorDePuntos() {
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
