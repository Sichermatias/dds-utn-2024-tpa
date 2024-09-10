package dominio.contacto.ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
@Setter@Getter
public class Ubicacion {
    @Column(name = "direccion", columnDefinition = "VARCHAR(255)")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;
}
