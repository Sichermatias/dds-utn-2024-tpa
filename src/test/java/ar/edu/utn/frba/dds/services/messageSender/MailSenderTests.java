package ar.edu.utn.frba.dds.services.messageSender;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.MailSender;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


public class MailSenderTests {

    @Test
    void enviarMail(){
        MailSender mailSender = new MailSender();

        Mensaje mensaje = new Mensaje("sicher2001@gmail.com", "Hola, estamos probando el envio de mails desde Java. Saludos!");

        mailSender.enviarMail(mensaje);
    }

    @Test
    void enviarMailMockeado(){
        MailSender mailSender = mock(MailSender.class);

        Mensaje mensaje = new Mensaje("unmail@gmail.com", "Un mensaje de prueba");

        doNothing().when(mailSender).enviarMail(mensaje);
        mailSender.enviarMail(mensaje);
    }
}
