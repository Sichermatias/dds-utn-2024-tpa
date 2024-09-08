package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Getter @Setter
public class HostearHeladera{
    private Heladera heladera;
    private static Double factorDePuntaje;

    @OneToOne
    @JoinColumn(name = "colaboracion_id")
    private Colaboracion colaboracion;
    private boolean cuentaParaPuntaje;
    public HostearHeladera() {
        this.cuentaParaPuntaje=true;
        this.colaboracion = new Colaboracion();
        factorDePuntaje = 5.0;
    }
    public Double puntaje() {
        this.heladera.actualizarMesesSinContarParaPuntaje();
        Double puntaje = this.heladera.getMesesSinContarParaElPuntaje() * factorDePuntaje;
        this.heladera.setMesesSinContarParaElPuntaje(0);
        this.heladera.setUltimaFechaContadaParaPuntaje(LocalDate.now());
        return puntaje;
    }
}
