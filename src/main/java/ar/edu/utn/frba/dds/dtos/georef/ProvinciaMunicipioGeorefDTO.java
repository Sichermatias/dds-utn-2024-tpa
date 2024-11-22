package ar.edu.utn.frba.dds.dtos.georef;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProvinciaMunicipioGeorefDTO {
    private ParametrosDTO parametros;
    private UbicacionDTO ubicacion;

    public String getProvinciaNombre() {
        return ubicacion != null && ubicacion.getProvincia() != null ? ubicacion.getProvincia().getNombre() : null;
    }

    public String getMunicipioNombre() {
        return ubicacion != null && ubicacion.getMunicipio() != null ? ubicacion.getMunicipio().getNombre() : null;
    }
}

@Getter
@Setter
class ParametrosDTO {
    private List<String> campos;
    private double lat;
    private double lon;
}

@Getter
@Setter
class UbicacionDTO {
    private double lat;
    private double lon;
    private MunicipioDTO municipio;
    private ProvinciaDTO provincia;
}

@Getter
@Setter
class MunicipioDTO {
    private String nombre;
}

@Getter
@Setter
class ProvinciaDTO {
    private String id;
    private String nombre;
}