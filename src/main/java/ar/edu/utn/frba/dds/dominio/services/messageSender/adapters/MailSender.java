package ar.edu.utn.frba.dds.dominio.services.messageSender.adapters;

import ar.edu.utn.frba.dds.dominio.services.messageSender.Mensaje;
import ar.edu.utn.frba.dds.dominio.utils.ConfigReader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender implements MailAdapter {
    private ConfigReader config;
    final String configPath = "mailconfig.properties";

    public MailSender() {
        this.config = new ConfigReader(configPath);
    }

    @Override
    public void enviarMail(Mensaje mensaje){
        Properties props;
        try {
            props = config.getProperties();
        } catch(Exception e) {
            System.out.println("Error al leer archivo de configuracion. Error: " + e);
            return;
        }

        final String username = props.getProperty("username"); //same fromMail
        final String password = props.getProperty("password");
        final String toEmail = mensaje.getReceptor();
        final String message = mensaje.getMensaje();

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, true));
            mimeMessage.setSubject(props.getProperty("subject"));
            mimeMessage.setText(message);
            Transport.send(mimeMessage);

        }catch (MessagingException me){
            System.out.println("Exception: " + me);
        }
    }
}