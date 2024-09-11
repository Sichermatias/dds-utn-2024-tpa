package dominio.persona;

import dominio.contacto.MedioDeContacto;
import dominio.contacto.ubicacion.Localidad;
import dominio.incidentes.Incidente;
import dominio.incidentes.VisitaIncidente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "tecnico")
@Getter @Setter
public class Tecnico {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "apellido", columnDefinition = "VARCHAR(50)")
    private String apellido;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "nroDocumento", columnDefinition = "INTEGER(11)")
    private Integer nroDocumento;

    @Column(name = "nroCuil", columnDefinition = "VARCHAR(50)")
    private Integer Cuil;

    @ManyToOne
    @JoinColumn(name = "medioDeContacto")
    private MedioDeContacto medioDeContacto;

    @ElementCollection
    @CollectionTable(name = "Localidad_id", joinColumns = @JoinColumn(name = "tecnico_id"))
    @JoinColumn(name = "localidad_id")
    private List<Localidad> localidadesDeServicio;

    @OneToMany //debería tener una lista de visitaIncidente?
    @JoinColumn(name = "tecnico_id")
    private List<Incidente> incidentesAsignados;

    public Tecnico(String nombre, String apellido, Integer cuil, TipoDocumento tipoDocumento,
                   Integer nroDocumento, MedioDeContacto medioDeContacto, List<Localidad> localidades,List<Incidente> incidentesAsignados) {
        this.nombre = nombre;
        this.apellido = apellido;
        Cuil = cuil;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.medioDeContacto = medioDeContacto;
        this.localidadesDeServicio = localidades;
        this.incidentesAsignados = incidentesAsignados;
    }

    public void agregarIncidente(Incidente incidente){
        //agrega incidente asignado a la lista
        incidentesAsignados.add(incidente);
    }

    public boolean resolverIncidente(Incidente incidenteAResolver){
        boolean resolucion = false;

        //Le pido al tecnico la informacion de la visita (foto opcional y si lo pudo resolver
        System.out.println("cargue Path a la foto (opcional):");
        Scanner scanner = new Scanner(System.in);
        String fotoVisita = scanner.nextLine();

        System.out.println("Indique si pudo resolver el incidente: S/N");
        String resolucionIncidente = scanner.nextLine();

        if(resolucionIncidente.equals("S")){
            resolucion = true;
            // TODO: falta agregar Persistente
            // incidenteAResolver.getHeladeraIncidente().setActivo(true);
        }

        new VisitaIncidente(this,incidenteAResolver,LocalDateTime.now(),fotoVisita,resolucion);

        return resolucion;
    }
}
