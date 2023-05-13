package br.com.mimobella.services;

import br.com.mimobella.models.Usuario;
import br.com.mimobella.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Component
public class TarefaAutomatizadaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SendEnvioEmailService send;

    private String templateEmail;


@PostConstruct
public void init() {
    try {
        // Leia o conteúdo do arquivo "template_email.html"
        templateEmail = Files.readString(Paths.get("src/main/resources/templates/template_email.html"));
    } catch (IOException e) {
        e.printStackTrace();
    }
}
//    @Scheduled(initialDelay = 2000, fixedDelay = 86400000) /*Roda a Cada 24 Horas*/
    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo") /*Vai rodar todos os dias as 11 da manhã com horario de Brasilia */
    public void notificarUserSenha() throws MessagingException, UnsupportedEncodingException {
        List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();

        for (Usuario usuario : usuarios) {
            try {
                String templateEmail = Files.readString(Paths.get("src/main/resources/templates/template_troca_senha.html"));

                String mensagem = templateEmail.replace("{{nome}}", usuario.getPessoa().getNome())
                        .replace("{{mensagem}}", "Está na hora de trocar sua senha de acesso a nossa Loja MimoBella, pois já faz mais de 90 dias e a mesma está vencida")
                        .replace("{{observacao}}", "Não responder este email");

                send.enviarEmailHtml("Troca de Senha de acesso a loja MimoBella", mensagem, usuario.getLogin());

                Thread.sleep(3000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
