package br.com.mimobella.controllers;

import br.com.mimobella.configs.ExcepetionJava;
import br.com.mimobella.models.PessoaFisica;
import br.com.mimobella.models.PessoaJuridica;
import br.com.mimobella.repositories.PessoaRepository;
import br.com.mimobella.services.PessoaUserService;
import br.com.mimobella.util.ValidaCPF;
import br.com.mimobella.util.ValidadorCnpj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PessoaUserService pessoaUserService;

    @ResponseBody
    @PostMapping(value = "**/salvarPj")
    public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExcepetionJava {

        if (pessoaJuridica == null){
            throw new ExcepetionJava("Pessoa Juridica não pode ser null");
        }
        if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpj(pessoaJuridica.getCnpj()) != null) {
            throw new ExcepetionJava("Ja existe um cadastro com o CNPJ: " + pessoaJuridica.getCnpj());
        }

        if (pessoaJuridica.getId() == null && pessoaRepository.existeIe(pessoaJuridica.getInscEstadual()) != null) {
            throw new ExcepetionJava("Ja existe um cadastro com a Inscrição Estadual: " + pessoaJuridica.getInscEstadual());
        }
        if (!ValidadorCnpj.isCNPJ(pessoaJuridica.getCnpj())){
            throw new ExcepetionJava("CNPJ: " + pessoaJuridica.getCnpj() + "não é um CNPJ válido, verifique! ");
        }

        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);


        return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);

    }

    /* Salvar Pessoa Fisica */
    @ResponseBody
    @PostMapping(value = "**/salvarPf")
    public ResponseEntity<PessoaFisica> salvarPf(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExcepetionJava {

        if (pessoaFisica == null){
            throw new ExcepetionJava("Pessoa Fisica não pode ser null");
        }
        if (pessoaFisica.getId() == null && pessoaRepository.existeCpf(pessoaFisica.getCpf()) != null) {
            throw new ExcepetionJava("Ja existe um cadastro com o CPF: " + pessoaFisica.getCpf());
        }
        if (!ValidaCPF.isCPF(pessoaFisica.getCpf())){
            throw new ExcepetionJava("CPF: " + pessoaFisica.getCpf() + "não é um CPF válido, verifique! ");
        }

        pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);


        return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);

    }
}
