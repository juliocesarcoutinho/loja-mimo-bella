package br.com.mimobella;

import br.com.mimobella.configs.ExcepetionJava;
import br.com.mimobella.controllers.PessoaController;
import br.com.mimobella.models.PessoaFisica;
import br.com.mimobella.models.PessoaJuridica;
import br.com.mimobella.repositories.PessoaRepository;
import br.com.mimobella.services.PessoaUserService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaMimoBellaApplication.class)
class PessoaTestUser extends TestCase {

//    @Autowired
//    private PessoaUserService pessoaUserService;
//
//    @Autowired
//    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaController pessoaController;

    @Test
    public void testCadastraPessoa() throws ExcepetionJava {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome("Larissa");
        pessoaJuridica.setRazaoSocial("Larissa e Milena Contábil ME");
        pessoaJuridica.setNomeFantasia("Larissa Contabilidades");
        pessoaJuridica.setCnpj("074.033.152.651");
        pessoaJuridica.setInscEstadual("074.033.152.651");
        pessoaJuridica.setInscMunicipal("");
        pessoaJuridica.setEmail("teste@test.com.br");
        pessoaJuridica.setTelefone("(16)3651-8966");

        pessoaController.salvarPj(pessoaJuridica);


//        PessoaFisica pessoaFisica = new PessoaFisica();
//        pessoaFisica.setCpf("976.020.568-84");
//        pessoaFisica.setNome("Victor Hugo da Cunha");
//        pessoaFisica.setEmail("victor.hugo.dacunha@adiretoria.com.br");
//        pessoaFisica.setTelefone("(14)2607-7658");
//        pessoaFisica.setEmpresa(pessoaFisica);

    }

}
