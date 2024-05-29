package dominio.services.messageSender.adapters;

import dominio.services.messageSender.Mensaje;
import dominio.services.utils.ConfigReader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender implements MailAdapter {
    private ConfigReader config;
    final String configPath = "../../config/mailconfig.properties";

    public MailSender() {
        this.config = new ConfigReader(configPath);
    }

    @Override
    public void enviarMail(Mensaje mensaje){
        Properties props = new Properties();
        //TODO: Hacer que lea las props del archivo de configuracion
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        final String userName = "dds2024g12@gmail.com"; //same fromMail
        final String password = "hftq zefj hdgc hmbs";
        final String toEmail = mensaje.getReceptor();
        final String message = mensaje.getMensaje();

        //NO SE PARA QUE SIRVEN
        // props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        // props.put("mail.smtp.ssl.enable", "true");
        // props.put("mail.smtp.socketFactory.port", "587"); //TLS Port
        //Session session = Session.getDefaultInstance(props);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(userName, password);

            }
        });

        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, true));
            mimeMessage.setSubject("Prueba");
            mimeMessage.setText(message);
            Transport.send(mimeMessage);

        }catch (MessagingException me){
            System.out.println("Exception: "+me);

        }
    }
}