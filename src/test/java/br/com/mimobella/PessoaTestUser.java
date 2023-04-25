package br.com.mimobella;

import br.com.mimobella.configs.ExcepetionJava;
import br.com.mimobella.controllers.PessoaController;
import br.com.mimobella.enums.TipoEndereco;
import br.com.mimobella.models.Endereco;
import br.com.mimobella.models.PessoaFisica;
import br.com.mimobella.models.PessoaJuridica;
import br.com.mimobella.repositories.PessoaRepository;
import br.com.mimobella.services.PessoaUserService;
import br.com.mimobella.services.SendEnvioEmailService;
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
        pessoaJuridica.setNome("Julio Cesar Coutinho");
        pessoaJuridica.setRazaoSocial("Carino Mata Posto ME");
        pessoaJuridica.setNomeFantasia("Posto Carino");
        pessoaJuridica.setCnpj("00.000.000/0001-00");
        pessoaJuridica.setInscEstadual("276.190.841.660");
        pessoaJuridica.setInscMunicipal("02987");
        pessoaJuridica.setEmail("julio@gemmap.com.br");
        pessoaJuridica.setTelefone("(16)2261-4596");

        Endereco enderecoCobranca = new Endereco();
        enderecoCobranca.setBairro("Vila Melgis");
        enderecoCobranca.setCep("18950-009");
        enderecoCobranca.setComplemento("Casa");
        enderecoCobranca.setRuaLogradouro("Rua do Lindos");
        enderecoCobranca.setNumero("1256");
        enderecoCobranca.setUf("SP");
        enderecoCobranca.setEmpresa(pessoaJuridica);
        enderecoCobranca.setTipoEndereco(TipoEndereco.COBRANCA);
        enderecoCobranca.setPessoa(pessoaJuridica);
        enderecoCobranca.setCidade("Iparssu");


        Endereco enderecoEntrega = new Endereco();
        enderecoEntrega.setBairro("Vila Garrocino");
        enderecoEntrega.setCep("18950-039");
        enderecoEntrega.setComplemento("Casa");
        enderecoEntrega.setRuaLogradouro("Rua dos Gatos");
        enderecoEntrega.setNumero("1256");
        enderecoEntrega.setUf("SP");
        enderecoEntrega.setEmpresa(pessoaJuridica);
        enderecoEntrega.setTipoEndereco(TipoEndereco.ENTREGA);
        enderecoEntrega.setPessoa(pessoaJuridica);
        enderecoEntrega.setCidade("Iparssu");

        pessoaJuridica.getEnderecos().add(enderecoEntrega);
        pessoaJuridica.getEnderecos().add(enderecoCobranca);


        pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();

        assertEquals(true, pessoaJuridica.getId() > 0);

        for (Endereco endereco : pessoaJuridica.getEnderecos()){
            assertEquals(true, endereco.getId() > 0);
        }
        assertEquals(2, pessoaJuridica.getEnderecos().size());


//        PessoaFisica pessoaFisica = new PessoaFisica();
//        pessoaFisica.setCpf("976.020.568-84");
//        pessoaFisica.setNome("Victor Hugo da Cunha");
//        pessoaFisica.setEmail("victor.hugo.dacunha@adiretoria.com.br");
//        pessoaFisica.setTelefone("(14)2607-7658");
//        pessoaFisica.setEmpresa(pessoaFisica);

    }

}
