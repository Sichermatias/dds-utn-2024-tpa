package dominio.persona;

import dominio.contacto.Ubicacion;

import java.time.LocalDate;

public class PersonaHumana extends Colaborador{
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String direccion;
    private Ubicacion ubicacion;
}