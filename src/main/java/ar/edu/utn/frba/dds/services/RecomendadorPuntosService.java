package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dtos.recomendador.RecomendadorPuntosDonacionReceiveDTO;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RecomendadorPuntosService {
        // URL del servicio externo
        private static final String URL_ATENCION_MEDICA = "https://virtserver.swaggerhub.com/THGONZALEZ/API_Recomendacion_de_Puntos_de_Donacion/1.0.0/api/recomendadorDePuntos";


        // Método para obtener los datos de localidades desde el servicio externo
        public static List<RecomendadorPuntosDonacionReceiveDTO> getPuntosRecomendadosDonacion(RecomendadorPuntosDonacionReceiveDTO puntosDonacionDTO) {

            try {
                URL url = new URL(URL_ATENCION_MEDICA);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                // Leer la respuesta y convertirla a una lista de LocalidadDto
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                RecomendadorPuntosDonacionReceiveDTO[] puntosDonacionArray = new Gson().fromJson(reader, RecomendadorPuntosDonacionReceiveDTO[].class);
                // Convertir el array a lista y devolver
                return Arrays.asList(puntosDonacionArray);
            } catch (Exception e) {

                e.printStackTrace();
                return List.of(); // Retorna una lista vacía en caso de error
            }
    }

}
