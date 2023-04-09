package br.com.mimobella;

import br.com.mimobella.controllers.AcessoController;
import br.com.mimobella.models.Acesso;
import br.com.mimobella.repositories.AcessoRepository;
import br.com.mimobella.services.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaMimoBellaApplication.class)
class LojaMimoBellaApplicationTests {

//    @Autowired
//    private AcessoRepository acessoRepository;
//
//    @Autowired
//    private AcessoService acessoService;

    @Autowired
    private AcessoController acessoController;

    @Test
    public void testCadastraAcesso(){
        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_ADMIN");
        acessoController.salvarAcesso(acesso);
        System.out.println("Acesso cadastrado com Sucesso e o id é: " + acesso.getId());
    }

    @Test
    void contextLoads() {
    }

}
