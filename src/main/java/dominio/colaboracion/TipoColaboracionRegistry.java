package dominio.colaboracion;

import dominio.archivos.carga_masiva.CampoInvalidoException;

import java.util.HashMap;
import java.util.Map;

public class TipoColaboracionRegistry {
    private static final Map<String, TipoColaboracionFactory> registry = new HashMap<>();

    static {
        registry.put("DINERO", DonacionDeDinero::new);
        registry.put("DONACION_VIANDAS", DonacionVianda::new);
        registry.put("REDISTRIBUCION_VIANDAS", Distribucion::new);
        registry.put("ENTREGA_TARJETAS", RegistrarPersonaVulnerable::new);
    }

    public static void register(String nombreTipo, TipoColaboracionFactory factory) {
        registry.put(nombreTipo.toUpperCase(), factory);
    }

    public static TipoColaboracion create(String nombreTipo) throws CampoInvalidoException {
        TipoColaboracionFactory factory = registry.get(nombreTipo.toUpperCase());
        if (factory == null) {
            throw new CampoInvalidoException("Forma de colaboración inválida: " + nombreTipo);
        }
        return factory.crear();
    }
}
