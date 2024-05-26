package dominio.persona;

import dominio.contacto.MedioDeContacto;
import dominio.contacto.ubicacion.Localidad;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Tecnico {
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private Integer nroDocumento;
    private Integer Cuil;
    private MedioDeContacto medioDeContacto;
    private List<Localidad> localidades;

    public Tecnico(String nombre, String apellido, Integer cuil, TipoDocumento tipoDocumento,
                   Integer nroDocumento, MedioDeContacto medioDeContacto, List<Localidad> localidades) {
        this.nombre = nombre;
        this.apellido = apellido;
        Cuil = cuil;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.medioDeContacto = medioDeContacto;
        this.localidades = localidades;
    }
}
