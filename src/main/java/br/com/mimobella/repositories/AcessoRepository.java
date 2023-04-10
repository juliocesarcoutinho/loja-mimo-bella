package br.com.mimobella.repositories;

import br.com.mimobella.models.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {

    @Query("SELECT a FROM Acesso a WHERE UPPER(TRIM(a.descricao)) LIKE %?1%")
    List<Acesso> buscarAcessoDesc(String desc);
}
