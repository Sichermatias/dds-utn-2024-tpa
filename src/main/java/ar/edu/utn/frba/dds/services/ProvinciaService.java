package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Provincia;
import ar.edu.utn.frba.dds.dtos.georef.ProvinciaMunicipioGeorefDTO;
import ar.edu.utn.frba.dds.dtos.georef.PuntoGeorefDTO;
import ar.edu.utn.frba.dds.models.repositories.imp.LocalidadRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.ProvinciaRepositorio;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProvinciaService {
    ProvinciaRepositorio repositorio = new ProvinciaRepositorio();

    public Provincia getProvincia(String provinciaNombre) {
        List<Provincia> provinciaBuscada = this.buscarProvincia(provinciaNombre);

        if (provinciaBuscada.isEmpty()){
            Provincia provincia = new Provincia();
            provincia.setNombreProvincia(provinciaNombre);

            return provincia;
        } else {
            return provinciaBuscada.get(0);
        }
    }

    public List<Provincia> buscarProvincia (String provinciaNombre) {
        return repositorio.buscarPorNombre(Provincia.class, provinciaNombre);
    }

}
