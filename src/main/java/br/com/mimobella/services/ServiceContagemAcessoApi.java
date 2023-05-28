package br.com.mimobella.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceContagemAcessoApi {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void atualizaAcessoEndPointPf(){
        jdbcTemplate.execute("BEGIN; UPDATE tabela_acesso_end_point " +
                "SET qtd_acesso_end_point = qtd_acesso_end_point + 1" +
                "WHERE nome_end_point = 'END_POINT_NOME_PESSOA_FISICA'; commit; ");
    }
}
