package br.com.mimobella.services;

import br.com.mimobella.models.PessoaJuridica;
import br.com.mimobella.models.Usuario;
import br.com.mimobella.repositories.PessoaRepository;
import br.com.mimobella.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica){

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

        }
        return juridica;
    }


}
