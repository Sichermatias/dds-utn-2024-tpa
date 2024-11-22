package ar.edu.utn.frba.dds.dtos.georef;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuntoGeorefDTO {
    public String latitud;
    public String longitud;
    public String campos = "campos=provincia.nombre,municipio.nombre";

    public PuntoGeorefDTO(String lat, String lon){
        latitud = lat;
        longitud = lon;
    }

    public String getAsQueryParams() {
        return "?" + "lat=" + latitud + "&lon=" + longitud + "&" + campos;
    }
}
