package ar.edu.utn.frba.dds.services.georef;

import ar.edu.utn.frba.dds.dtos.georef.ProvinciaMunicipioGeorefDTO;
import ar.edu.utn.frba.dds.dtos.georef.PuntoGeorefDTO;
import ar.edu.utn.frba.dds.services.GeorefService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeorefTests {

    @Test
    void sendRequest() {
        try {
            PuntoGeorefDTO punto = new PuntoGeorefDTO("-34.5984832639182", "-58.4202537685633");
            ProvinciaMunicipioGeorefDTO provinciaMunicipio = GeorefService.getProvinciaMunicipio(punto);

            assertEquals("Comuna 5", provinciaMunicipio.getMunicipioNombre());
            assertEquals("Ciudad Autónoma de Buenos Aires", provinciaMunicipio.getProvinciaNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}