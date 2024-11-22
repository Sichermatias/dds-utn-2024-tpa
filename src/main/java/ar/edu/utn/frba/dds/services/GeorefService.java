package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dtos.georef.ProvinciaMunicipioGeorefDTO;
import ar.edu.utn.frba.dds.dtos.georef.PuntoGeorefDTO;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeorefService {
        // URL del servicio externo
        private static final String URL_GEOREF = "https://apis.datos.gob.ar/georef/api/";

        public static ProvinciaMunicipioGeorefDTO getProvinciaMunicipio(PuntoGeorefDTO punto) throws Exception {
            try {
                URL url = new URL(URL_GEOREF + "ubicacion" + punto.getAsQueryParams());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());

                ProvinciaMunicipioGeorefDTO provinciaMunicipio = new Gson().fromJson(reader, ProvinciaMunicipioGeorefDTO.class);

                return provinciaMunicipio;
            } catch (Exception e) {

                e.printStackTrace();
                throw new Exception("Hubo un error al hacer la consulta"); // Retorna una lista vacía en caso de error
            }
        }

}
