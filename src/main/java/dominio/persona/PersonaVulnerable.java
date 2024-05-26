package dominio.persona;

import dominio.colaboracion.UsoDeHeladera;
import dominio.contacto.ubicacion.Ubicacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonaVulnerable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;
    private Ubicacion ubicacion;
    private TipoDocumento tipoDocumento;
    private Integer nroDocumento;
    private String codigoTarjeta;
    private List<UsoDeHeladera> usosDeHeladeras;
    private List<PersonaVulnerable> personasVulnACargo = new ArrayList<>();
    private Integer cantUsosMaximosPorDia = 4;
    private Integer usosDelDia;

    public void calcularUsosMaxPorDia(){
        this.cantUsosMaximosPorDia += Math.toIntExact(this.personasVulnACargo.stream().count()) * 2;
    }

    public PersonaVulnerable(String nombre, LocalDate fechaNacimiento, LocalDate fechaRegistro,
                             Ubicacion ubicacion, TipoDocumento tipoDocumento, Integer nroDocumento,
                             String codigoTarjeta, List<PersonaVulnerable> personasVulnACargo) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.ubicacion = ubicacion;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.codigoTarjeta = codigoTarjeta;
        this.personasVulnACargo = personasVulnACargo;
    }
}
