package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class EncargarseDeHeladera extends TipoColaboracion{
    private Heladera heladera;
    private static Double factorDePuntaje;

    public EncargarseDeHeladera() {
        factorDePuntaje = 5.0;
    }

    @Override
    public Double puntaje() {
        this.heladera.actualizarMesesSinContarParaPuntaje();
        Double puntaje = this.heladera.getMesesSinContarParaElPuntaje() * factorDePuntaje;
        this.heladera.setMesesSinContarParaElPuntaje(0);
        this.heladera.setUltimaFechaContadaParaPuntaje(LocalDate.now());
        return puntaje;
    }
}
