package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.dominio.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.dominio.colaboracion.PedidoDeApertura;
import ar.edu.utn.frba.dds.dominio.colaboracion.Transaccion;
import ar.edu.utn.frba.dds.dominio.persona.Colaborador;
import ar.edu.utn.frba.dds.dominio.utils.ConfigReader;
import ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.HeladeraRepositorio;
import ar.edu.utn.frba.dds.models.repositories.imp.PedidoDeAperturaRepositorio;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class PedidoDeAperturaService {
    private final PedidoDeAperturaRepositorio pedidoDeAperturaRepositorio;
    private final ColaboracionRepositorio colaboracionRepositorio;
    private final ConfigReader config;
    final String configPath = "aperturaHeladera.properties";


    public PedidoDeAperturaService(PedidoDeAperturaRepositorio pedidoDeAperturaRepositorio, ColaboracionRepositorio colaboracionRepositorio){
        this.pedidoDeAperturaRepositorio = pedidoDeAperturaRepositorio;
        this.colaboracionRepositorio = colaboracionRepositorio;
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

        List<PedidoDeApertura> listaPedidosDeApertura = this.pedidoDeAperturaRepositorio.buscarPorEstado(PedidoDeApertura.class, true);

        for(PedidoDeApertura pedidoDeApertura : listaPedidosDeApertura){
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            long horasTranscurridas = Duration.between(pedidoDeApertura.getFechaHoraAlta(), fechaHoraActual).toHours();
            Integer viandasActuales = pedidoDeApertura.getHeladera().getCantViandasActuales();
            if(pedidoDeApertura.getFechaHoraRealizada() == null && horasTranscurridas >= tiempoMaximoEspera){
                pedidoDeApertura.setValido(false);
                pedidoDeApertura.setFechaHoraBaja(fechaHoraActual);
                Integer cantidadViandasPedido = pedidoDeApertura.getCantidadViandas();
                pedidoDeApertura.getHeladera().setCantViandasActuales(viandasActuales - cantidadViandasPedido);

                Colaboracion colaboracion = this.colaboracionRepositorio.obtenerColaboracionPorPedidoApertura(pedidoDeApertura).get(0);
                colaboracion.setActivo(false);
                colaboracion.setFechaHoraBaja(fechaHoraActual);

                Colaborador colaborador = colaboracion.getColaborador();

                Transaccion transaccion = colaboracion.getTransaccion();
                if (transaccion.getActivo()){
                    transaccion.setActivo(false);
                    transaccion.setFechaHoraBaja(fechaHoraActual);
                    colaborador.setPuntaje(colaborador.getPuntaje() - transaccion.getMontoPuntaje());
                }

                if (colaboracion.getTipo().equals("DONACION_VIANDAS")){
                    //Le resto la cantidad de viandas donadas en la semana
                    colaborador.setCantSemanalViandasDonadas(colaborador.getCantSemanalViandasDonadas() - cantidadViandasPedido);
                }

                pedidoDeAperturaRepositorio.actualizar(pedidoDeApertura);
                colaboracionRepositorio.actualizar(colaboracion);
                System.out.println("El pedido de apertura: " + pedidoDeApertura.getId() + " fue cancelado" );
            }

        }


    }
}
