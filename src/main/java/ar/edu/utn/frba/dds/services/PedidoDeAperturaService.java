package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.dominio.colaboracion.PedidoDeApertura;
import ar.edu.utn.frba.dds.dominio.utils.ConfigReader;
import ar.edu.utn.frba.dds.models.repositories.imp.PedidoDeAperturaRepositorio;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class PedidoDeAperturaService {
    private final PedidoDeAperturaRepositorio pedidoDeAperturaRepositorio;
    private final ConfigReader config;
    final String configPath = "aperturaHeladera.properties";


    public PedidoDeAperturaService(PedidoDeAperturaRepositorio pedidoDeAperturaRepositorio){
        this.pedidoDeAperturaRepositorio = pedidoDeAperturaRepositorio;
        this.config = new ConfigReader(configPath);
    }

    public void actualizarPedidosDeApertura(){
        Properties props;
        try {
            props = config.getProperties();
        } catch(Exception e) {
            System.out.println("Error al leer archivo de configuracion. Error: " + e);
            return;
        }
        int tiempoMaximoEspera = Integer.parseInt(props.getProperty("tiempoMaximoEspera"));

        List<PedidoDeApertura> listaPedidosDeApertura = this.pedidoDeAperturaRepositorio.buscarPorEstado(PedidoDeApertura.class, "true");

        for(PedidoDeApertura pedidoDeApertura : listaPedidosDeApertura){
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            long horasTranscurridas = Duration.between(pedidoDeApertura.getFechaHoraRealizada(), fechaHoraActual).toHours();
            Integer viandasActuales = pedidoDeApertura.getHeladera().getCantViandasActuales();
            if(horasTranscurridas >= tiempoMaximoEspera){
                pedidoDeApertura.setValido(false);
                pedidoDeApertura.setFechaHoraBaja(fechaHoraActual);
                this.pedidoDeAperturaRepositorio.actualizar(pedidoDeApertura);
                System.out.println("El pedido de apertura: " + pedidoDeApertura.getId() + " fue cancelado" );
            }

        }


    }
}
