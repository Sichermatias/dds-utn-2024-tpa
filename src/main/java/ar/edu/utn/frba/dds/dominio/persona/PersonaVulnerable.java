package ar.edu.utn.frba.dds.dominio.persona;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Setter
@Getter
@Entity
@Table(name = "personaVulnerable")
public class PersonaVulnerable extends Persistente {
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "apellido", columnDefinition = "VARCHAR(50)")
    private String apellido;

    @Column(name = "fechaNacimiento", columnDefinition = "DATE")
    private LocalDate fechaNacimiento;

    @Embedded
    private Ubicacion ubicacion;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "nroDocumento", columnDefinition = "INTEGER(11)")
    private String nroDocumento;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "personaVulnerable_id")
    private List<Tarjeta> tarjetas;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "hijoVulnerable_padreVulnerable",
            joinColumns = @JoinColumn(name = "padre_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hijo_id", referencedColumnName = "id")
    )
    private List<PersonaVulnerable> personasVulnACargo;

    //TODO: llevarlo a una config
    @Column(name = "cantUsosMaximosPorDia", columnDefinition = "TINYINT")
    private Integer cantUsosMaximosPorDia = 0;

    @Column(name = "usosDelDia", columnDefinition = "TINYINT")
    private Integer usosDelDia;

    public PersonaVulnerable() {
        this.personasVulnACargo = new ArrayList<>();
        this.tarjetas = new ArrayList<>();
    }

    public void calcularUsosMaxPorDia(){
        this.cantUsosMaximosPorDia += Math.toIntExact(this.personasVulnACargo.stream().count()) * 2;
    }

    public void agregarPersonasVulnerablesACargo(PersonaVulnerable ... personasVulnerables) {
        Collections.addAll(this.personasVulnACargo, personasVulnerables);
    }
    public void cambiarTarjeta(Tarjeta tarjeta){
        tarjetas.add(0,tarjeta);
    }
}
