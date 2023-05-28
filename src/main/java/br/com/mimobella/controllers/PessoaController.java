package br.com.mimobella.controllers;

import br.com.mimobella.configs.ExcepetionJava;
import br.com.mimobella.dtos.CepDTO;
import br.com.mimobella.dtos.ConsultaCnpjDTO;
import br.com.mimobella.models.Endereco;
import br.com.mimobella.models.PessoaFisica;
import br.com.mimobella.models.PessoaJuridica;
import br.com.mimobella.repositories.EnderecoRepository;
import br.com.mimobella.repositories.PessoaFisicaRepository;
import br.com.mimobella.repositories.PessoaRepository;
import br.com.mimobella.services.PessoaUserService;
import br.com.mimobella.services.ServiceContagemAcessoApi;
import br.com.mimobella.util.ValidaCPF;
import br.com.mimobella.util.ValidadorCnpj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PessoaController {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PessoaUserService pessoaUserService;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ServiceContagemAcessoApi serviceContagemAcessoApi;

    @ResponseBody
    @GetMapping(value = "**/consultaCep/{cep}")
    public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) {
        CepDTO cepDTO = pessoaUserService.consultaCep(cep);
        return new ResponseEntity<CepDTO>(cepDTO, HttpStatus.OK);
    }


    /*End point para consulta de cnpj*/
    @ResponseBody
    @GetMapping(value = "**/consultaCnpjWs/{cnpj}")
    public ResponseEntity<ConsultaCnpjDTO> consultaCnpjWs(@PathVariable("cnpj") String cnpj) {
        ConsultaCnpjDTO consultaCnpjDTO = pessoaUserService.consultaCnpjWs(cnpj);
        return new ResponseEntity<ConsultaCnpjDTO>(consultaCnpjDTO, HttpStatus.OK);

    }

    /*Lista uma pessoa Juridica por nome*/
    @ResponseBody
    @GetMapping(value = "**/consultaNomePj/{nome}")
    public ResponseEntity <List<PessoaJuridica>> consultaNomePj(@PathVariable("nome") String nome){
        List<PessoaJuridica> pessoaJuridicas = pessoaRepository.pesquisaPorNome(nome.trim().toUpperCase());


        return new ResponseEntity<List<PessoaJuridica>>(pessoaJuridicas, HttpStatus.OK);
    }

    /*Lista uma pessoa Juridica por cnpj*/
    @ResponseBody
    @GetMapping(value = "**/consultaCnpj/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultaCnpj(@PathVariable("cnpj") String cnpj){
        List<PessoaJuridica> pessoaJuridicas = pessoaRepository.existeCnpjList(cnpj);
        return new ResponseEntity<List<PessoaJuridica>>(pessoaJuridicas, HttpStatus.OK);

    }

    /*salva uma pessoa Juridica*/
    @ResponseBody
    @PostMapping(value = "**/salvarPj")
    public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExcepetionJava {

        if (pessoaJuridica == null) {
            throw new ExcepetionJava("Pessoa Juridica não pode ser null");
        }

        if (pessoaJuridica.getTipoPessoa() == null) {
            throw new ExcepetionJava("Informe o tipo Pessoa, Juridico ou Fornecedor da Loja");

        }

        if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpj(pessoaJuridica.getCnpj()) != null) {
            throw new ExcepetionJava("Ja existe um cadastro com o CNPJ: " + pessoaJuridica.getCnpj());
        }

        if (pessoaJuridica.getId() == null && pessoaRepository.existeIe(pessoaJuridica.getInscEstadual()) != null) {
            throw new ExcepetionJava("Ja existe um cadastro com a Inscrição Estadual: " + pessoaJuridica.getInscEstadual());
        }
        if (!ValidadorCnpj.isCNPJ(pessoaJuridica.getCnpj())) {
            throw new ExcepetionJava("CNPJ: " + pessoaJuridica.getCnpj() + "não é um CNPJ válido, verifique! ");
        }

        if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
            for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
                CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
                pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
                pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
                pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
                pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(cepDTO.getLogradouro());
                pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
            }
        } else {
            for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
                Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
                if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
                    CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
                    pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
                    pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
                    pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
                    pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(cepDTO.getLogradouro());
                    pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
                }

            }
        }

        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);


        return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);

    }

}
