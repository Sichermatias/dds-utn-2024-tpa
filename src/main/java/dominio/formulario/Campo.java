package dominio.formulario;

import java.util.ArrayList;

public class Campo {
    private String nombre;
    private String descripcion;
    private TipoCampo tipo;
    private ArrayList<Opcion> opciones= new ArrayList<>();
    private Boolean esObligatorio;
}
