package br.com.mimobella.repositories;

import br.com.mimobella.models.Pessoa;
import br.com.mimobella.models.PessoaFisica;
import br.com.mimobella.models.PessoaJuridica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends CrudRepository<PessoaJuridica, Long> {

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
    public PessoaJuridica existeCnpj(String cnpj);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
    public List<PessoaJuridica> existeCnpjList(String cnpj);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
    public PessoaFisica existeCpf(String cpf);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
    public List<PessoaFisica> existeCpfLista(String cpf);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.inscEstadual = ?1")
    public PessoaJuridica existeIe(String inscEstadual);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.inscEstadual = ?1")
    public List<PessoaJuridica> existeIeList(String inscEstadual);

}
