package br.com.mimobella.controllers;

import br.com.mimobella.configs.ExcepetionJava;
import br.com.mimobella.enums.TipoPessoa;
import br.com.mimobella.models.PessoaFisica;
import br.com.mimobella.repositories.PessoaFisicaRepository;
import br.com.mimobella.services.PessoaUserService;
import br.com.mimobella.services.ServiceContagemAcessoApi;
import br.com.mimobella.util.ValidaCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PessoaFisicaController {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;
    @Autowired
    private PessoaUserService pessoaUserService;
    @Autowired
    private ServiceContagemAcessoApi serviceContagemAcessoApi;



    /*Lista por nome*/
    @ResponseBody
    @GetMapping(value = "**/consultarPfPorNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultarPfPorNome(@PathVariable("nome") String nome){

        List<PessoaFisica> pessoaFisica = pessoaFisicaRepository.pesquisaPorNomePf(nome.trim().toUpperCase());
        serviceContagemAcessoApi.atualizaAcessoEndPointPf();
        return new ResponseEntity<List<PessoaFisica>>(pessoaFisica, HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping(value = "**/consultarPorCpf/{cpf}")
    public ResponseEntity<List<PessoaFisica>> consultPorCpf(@PathVariable("cpf") String cpf){

        List<PessoaFisica> pessoaFisica = pessoaFisicaRepository.pesquisaPorCpf(cpf.trim().toUpperCase());
        return new ResponseEntity<List<PessoaFisica>>(pessoaFisica, HttpStatus.OK);

    }

    /*Salva uma pessoa fisica no banco */
    /* Salvar Pessoa Fisica */
    @ResponseBody
    @PostMapping(value = "**/salvarPf")
    public ResponseEntity<PessoaFisica> salvarPf(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExcepetionJava {

        if (pessoaFisica == null) {
            throw new ExcepetionJava("Pessoa Fisica não pode ser null");
        }
        if (pessoaFisica.getTipoPessoa() == null) {
            pessoaFisica.setTipoPessoa(TipoPessoa.valueOf(TipoPessoa.FISICA.name()));
        }
        if (pessoaFisica.getId() == null && pessoaFisicaRepository.pesquisaPorCpf(pessoaFisica.getCpf()) != null) {
            throw new ExcepetionJava("Ja existe um cadastro com o CPF: " + pessoaFisica.getCpf());
        }
        if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
            throw new ExcepetionJava("CPF: " + pessoaFisica.getCpf() + "não é um CPF válido, verifique! ");
        }
        pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
        return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);

    }
}
