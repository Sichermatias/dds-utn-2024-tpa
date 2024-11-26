package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Provincia;
import ar.edu.utn.frba.dds.dtos.georef.ProvinciaMunicipioGeorefDTO;
import ar.edu.utn.frba.dds.dtos.georef.PuntoGeorefDTO;
import ar.edu.utn.frba.dds.models.repositories.imp.LocalidadRepositorio;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LocalidadService {

    LocalidadRepositorio repositorio = new LocalidadRepositorio();

    public List<Localidad> buscarLocalidad (String localidadNombre) {
        return repositorio.buscarPorNombre(Localidad.class , localidadNombre);
    }

    public Localidad getLocalidad(ProvinciaMunicipioGeorefDTO provinciaMunicipio) {
        List<Localidad> localidadBuscada = this.buscarLocalidad(provinciaMunicipio.getMunicipioNombre());

        if (localidadBuscada.isEmpty()){
            ProvinciaService provinciaService = new ProvinciaService();
            Provincia provincia = provinciaService.getProvincia(provinciaMunicipio.getProvinciaNombre());

            Localidad localidad = new Localidad();
            localidad.setNombreLocalidad(provinciaMunicipio.getMunicipioNombre());
            localidad.setProvincia(provincia);

            return localidad;
        } else {
            return localidadBuscada.get(0);
        }


    }
}
