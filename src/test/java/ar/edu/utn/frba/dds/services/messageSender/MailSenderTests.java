package ar.edu.utn.frba.dds.services.messageSender;

import dominio.services.messageSender.Mensaje;
import dominio.services.messageSender.adapters.MailSender;
import org.junit.jupiter.api.Test;



public class MailSenderTests {

    @Test
    void enviarMail(){
        MailSender mailSender = new MailSender();
        Mensaje mensaje = new Mensaje("sicher2001@gmail.com", "Hola, estamos probando el envio de mails jejeeeeee");

        mailSender.enviarMail(mensaje);
    }
}
