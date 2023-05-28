package br.com.mimobella.repositories;

import br.com.mimobella.models.PessoaFisica;
import br.com.mimobella.models.PessoaJuridica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE upper(trim(pf.nome)) like %?1%")
    public List<PessoaFisica> pesquisaPorNomePf(String nome);

    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    public List<PessoaFisica> pesquisaPorCpf(String cpf);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
    public PessoaFisica existeCpf(String cpf);


}
