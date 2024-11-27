package ar.edu.utn.frba.dds.dominio.persona;

import ar.edu.utn.frba.dds.dominio.Persistente;
import ar.edu.utn.frba.dds.dominio.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.dominio.contacto.ubicacion.Localidad;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.VisitaIncidente;
import ar.edu.utn.frba.dds.dominio.persona.login.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "tecnico")
@Getter @Setter
public class Tecnico extends Persistente {
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
    private String nroDocumento;

    @Column(name = "nroCuil", columnDefinition = "VARCHAR(50)")
    private String Cuil;

    @Column(name = "telegramID", columnDefinition = "VARCHAR(50)")
    private String telegramID;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
    private List<MedioDeContacto> mediosDeContacto;

    @ElementCollection
    @CollectionTable(name = "Localidad_tecnico", joinColumns = @JoinColumn(name = "tecnico_id"))
    @JoinColumn(name = "localidad_id")
    private List<Localidad> localidadesDeServicio=new ArrayList<>();

    @OneToMany//debería tener una lista de visitaIncidente?
    @JoinColumn(name = "tecnico_id")
    private List<Incidente> incidentesAsignados=new ArrayList<>();


    //TODO HACERLO EN DIAGRAMA
    @Setter@Getter
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public Tecnico(String nombre, String apellido, String cuil, TipoDocumento tipoDocumento,
                   String nroDocumento, List<Localidad> localidades,List<Incidente> incidentesAsignados) {
        this.nombre = nombre;
        this.apellido = apellido;
        Cuil = cuil;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.mediosDeContacto = new ArrayList<>();
        this.localidadesDeServicio = localidades;
        this.incidentesAsignados = incidentesAsignados;
    }

    public Tecnico() {

    }

    public List<Localidad> getLocalidadesDeServicio(){
        return this.localidadesDeServicio;
    }
    public void agregarIncidente(Incidente incidente){
        //agrega incidente asignado a la lista
        incidentesAsignados.add(incidente);
        incidente.setAsignado(true);
    }
    public void removerIncidente(Incidente incidente){
        incidentesAsignados.remove(incidente);
    }
    public void agregarLocalidadServicio(Localidad localidad){
        localidadesDeServicio.add(localidad);
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

        new VisitaIncidente(this,incidenteAResolver,LocalDateTime.now(),resolucion);

        return resolucion;
    }


    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
        this.mediosDeContacto.add(medioDeContacto);
    }

    public MedioDeContacto getMedioDeContacto(String nombreMedio) {
        return this.getMediosDeContacto().stream()
                .filter(m -> m.getNombreDeMedioDeContacto().getNombre().equals(nombreMedio))
                .findAny().orElse(null);
    }
}
