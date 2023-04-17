package br.com.mimobella;

import br.com.mimobella.configs.ExcepetionJava;
import br.com.mimobella.controllers.AcessoController;
import br.com.mimobella.models.Acesso;
import br.com.mimobella.repositories.AcessoRepository;
import br.com.mimobella.services.AcessoService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@SpringBootTest(classes = LojaMimoBellaApplication.class)
class LojaMimoBellaApplicationTests extends TestCase {

    @Autowired
    private AcessoRepository acessoRepository;
//
//    @Autowired
//    private AcessoService acessoService;

    @Autowired
    private AcessoController acessoController;

    @Test
    public void testCadastraAcesso() throws ExcepetionJava {

        String descAcesso = "ROLE_ADMIN " + Calendar.getInstance().getTimeInMillis();

        Acesso acesso = new Acesso();
        acesso.setDescricao(descAcesso);

        assertEquals(true, acesso.getId() == null);

        acesso = acessoController.salvarAcesso(acesso).getBody();

        assertEquals(true, acesso.getId() > 0);

        assertEquals(descAcesso, acesso.getDescricao());

        System.out.println("Acesso cadastrado com Sucesso e o id é: " + acesso.getId());

        /*Teste de carregamento*/
        Acesso acesso1 = acessoRepository.findById(acesso.getId()).get();

        assertEquals(acesso.getId(), acesso1.getId());

        /*Teste de delete*/
        acessoRepository.deleteById(acesso1.getId());

        acessoRepository.flush();

        Acesso acesso2 = acessoRepository.findById(acesso1.getId()).orElse(null);
        assertEquals(true, acesso2 == null);

        /*TESTE DE QUERY*/

        acesso = new Acesso();
        acesso.setDescricao("ROLE_GERENTE " + Calendar.getInstance().getTimeInMillis());
        acesso = acessoController.salvarAcesso(acesso).getBody();

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc("GERENTE".toUpperCase().trim());

        assertEquals(1, acessos.size());
        acessoRepository.deleteById(acesso.getId());

    }

    @Test
    void contextLoads() {
    }

}
