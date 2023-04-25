package br.com.mimobella.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class SendEnvioEmailService {
    public String userName = "mimobella.ipaussu@outlook.com";
    public String senha = "MimoBella789456#";

    @Async
    public void enviarEmailHtml(String assunto, String mensagem, String emailDestino) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, senha);
            }
        });

        session.setDebug(true);

        Address [] toUser = InternetAddress.parse(emailDestino);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName, "Mimo-Bella", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(assunto);
        message.setContent(mensagem, "text/html; charset=utf-8");

        Transport transport = session.getTransport("smtp");
        transport.connect(userName, senha);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
