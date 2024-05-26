package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsoDeHeladera {
    private Heladera heladera;
    private LocalDateTime fechaHora;
    private Vianda vianda;
    private String codigoTarjeta;

    public void removerViandasDeHeladera(Vianda vianda){
        this.heladera.viandas.remove(vianda);
    }
}
