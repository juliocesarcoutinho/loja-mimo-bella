package br.com.mimobella.controllers;

import br.com.mimobella.models.Acesso;
import br.com.mimobella.services.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AcessoController {
    @Autowired
    private AcessoService acessoService;

    public Acesso salvarAcesso(Acesso acesso){

        return acessoService.save(acesso);
    }
}
