package dominio.colaboracion;

import dominio.infraestructura.Heladera;

import java.time.LocalDate;

public class EncargarseDeHeladera extends TipoColaboracion{
    private Heladera heladera;
    private static Double factorDePuntaje;

    public EncargarseDeHeladera() {
        factorDePuntaje = 5.0;
    }

    @Override
    public Double puntaje() {
        Double puntaje = heladera.getMesesSinContarParaElPuntaje() * factorDePuntaje;
        heladera.setMesesSinContarParaElPuntaje(0);
        heladera.setUltimaFechaContadaParaPuntaje(LocalDate.now());
        return puntaje;
    }
}
