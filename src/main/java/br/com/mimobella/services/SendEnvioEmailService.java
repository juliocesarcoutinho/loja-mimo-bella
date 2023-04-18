package br.com.mimobella.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SendEnvioEmailService {
    public String userName = "mimobella.ipaussu@gmail.com";
    public String senha = "Mimo147258#";

    @Async
    public void enviarEmailHtml(String assunto, String mensagem, String emailDestino){
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls", "false");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

    }
}
