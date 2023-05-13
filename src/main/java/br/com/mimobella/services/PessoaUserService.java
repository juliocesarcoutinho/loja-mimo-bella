package br.com.mimobella.services;

import br.com.mimobella.models.PessoaJuridica;
import br.com.mimobella.models.Usuario;
import br.com.mimobella.repositories.PessoaRepository;
import br.com.mimobella.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SendEnvioEmailService sendEnvioEmailService;

    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica){

//        juridica = pessoaRepository.save(juridica);

        for (int i = 0 ; i < juridica.getEnderecos().size(); i++){
            juridica.getEnderecos().get(i).setPessoa(juridica);
            juridica.getEnderecos().get(i).setEmpresa(juridica);
        }
        juridica = pessoaRepository.save(juridica);

        Usuario usuarioPj = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());

        if (usuarioPj == null) {

            String constraint = usuarioRepository.consultaConstranitAcesso();
            if (constraint != null){
                jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint + "; commit; ");
            }
           usuarioPj = new Usuario();
            usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPj.setEmpresa(juridica);
            usuarioPj.setPessoa(juridica);
            usuarioPj.setLogin(juridica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPj.setSenha(senhaCript);
            usuarioPj = usuarioRepository.save(usuarioPj);

            usuarioRepository.insereAcessoUserPj(usuarioPj.getId());
            usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");

            StringBuilder mensagemHtml = new StringBuilder();
            mensagemHtml.append("<b>Segue abaixo seus dados de acesso a Loja MimoBella</b></br> ");
            mensagemHtml.append("<b>Login:</b> " + juridica.getEmail() + "</br>");
            mensagemHtml.append("<b>Senha:</b> " + senha + "</br></br>");
            mensagemHtml.append("Obrigado pela Preferencia</br></br></br></br>");
            mensagemHtml.append("Obs: Não responder esse email");

            try {
                // Leia o conteúdo do arquivo "template_email.html"
                String templateEmail = Files.readString(Paths.get("src/main/resources/templates/template_email.html"));

                // Substitui as variáveis de substituição no modelo HTML pelo valor real
                templateEmail = templateEmail.replace("{{email}}", juridica.getEmail());
                templateEmail = templateEmail.replace("{{senha}}", senha);

                // Envie o e-mail usando o modelo personalizado
                sendEnvioEmailService.enviarEmailHtml("Acesso Gerado para Loja Virtual MimoBella", templateEmail, juridica.getEmail());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }


        }
        return juridica;
    }


}
