package br.com.mimobella.services;

import br.com.mimobella.models.Usuario;
import br.com.mimobella.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Component
public class TarefaAutomatizadaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SendEnvioEmailService send;

//    @Scheduled(initialDelay = 2000, fixedDelay = 86400000) /*Roda a Cada 24 Horas*/
    @Scheduled(cron = "0 0 11 * *", zone = "America/Sao_Paulo") /* Vai às 11 horas da manhã de cada dia horário de São Paulo */
    public void notificarUserSenha() throws MessagingException, UnsupportedEncodingException, InterruptedException {

        List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();
       for(Usuario usuario : usuarios){
           StringBuilder msg = new StringBuilder();
           msg.append("Olá " + usuario.getPessoa().getNome() + "</br>");
           msg.append("Está na hora de trocar sua senha de acesso a nossa Loja MimoBella, " +
                   "pois ja faz mais de 90 dias e a mesma esta vencida </br> ");
           msg.append("Obrigado pela preferência! </br></br></br></br></br>");
           msg.append("Obs: Não responder este email");

           send.enviarEmailHtml("Troca de Senha de acesso a loja MimoBella ", msg.toString(), usuario.getLogin());

            Thread.sleep(3000);

       }

    }

}
