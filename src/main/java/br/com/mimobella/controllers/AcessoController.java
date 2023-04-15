package br.com.mimobella.controllers;

import br.com.mimobella.configs.ExcepetionJava;
import br.com.mimobella.models.Acesso;
import br.com.mimobella.repositories.AcessoRepository;
import br.com.mimobella.services.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class AcessoController {
    @Autowired
    private AcessoService acessoService;
    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarAcesso")
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExcepetionJava {

        if (acesso.getId() == null) {
            List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
            if (!acessos.isEmpty()){
                throw new ExcepetionJava("Ja existe um acesso com a mesma descrição " + acesso.getDescricao());
            }
        }

        Acesso acessoSalvo = acessoService.save(acesso);

        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/deleteAcesso")
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso){

        acessoRepository.deleteById(acesso.getId());

        return new ResponseEntity<>("Acesso Removido ",HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteAcessoPorId/{id}")
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id){

        acessoRepository.deleteById(id);

        return new ResponseEntity<>("Acesso Removido ",HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/obterAcessoPorId/{id}")
    public ResponseEntity<Acesso> obterAcessoPorId(@PathVariable("id") Long id) throws ExcepetionJava {

        Acesso acesso = acessoRepository.findById(id).orElse(null);
        if (acesso == null) {
         throw new ExcepetionJava("Não foi localizado acesso com codigo: " + id);
        }

        return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/obterAcessoPorDesc/{desc}")
    public ResponseEntity<List<Acesso>> obterAcessoPorDesc(@PathVariable("desc") String desc){

        List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc.toUpperCase());

        return new ResponseEntity<List<Acesso>>(acesso,HttpStatus.OK);
    }

}
