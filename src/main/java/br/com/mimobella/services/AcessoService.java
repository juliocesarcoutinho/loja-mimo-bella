package br.com.mimobella.services;

import br.com.mimobella.models.Acesso;
import br.com.mimobella.repositories.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    public Acesso save(Acesso acesso){
        return acessoRepository.save(acesso);
    }
}
