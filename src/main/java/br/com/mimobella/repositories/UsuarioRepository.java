package br.com.mimobella.repositories;

import br.com.mimobella.models.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query(value = "SELECT u FROM Usuario u WHERE u.login = ?1")
    Usuario findUserByLogin(String login);

    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
    Usuario findUserByPessoa(Long id, String email);


    @Query(value = "select constraint_name from information_schema.constraint_column_usage\n" +
            "where table_name = 'usuario_acesso' and column_name = 'acesso_id'\n" +
            "and constraint_name <> 'unique_acesso_user';" , nativeQuery = true )
    String consultaConstranitAcesso();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuario_acesso(usuario_id, acesso_id)" +
            "values (?1, (SELECT id from acesso where descricao = 'ROLE_USER'))")
    void insereAcessoUserPj(Long idUser);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuario_acesso(usuario_id, acesso_id)" +
            "values (?1, (SELECT id from acesso where descricao = ?2))")
    void insereAcessoUserPj(Long idUser, String acesso);

    @Query(value = "SELECT u FROM Usuario u WHERE u.dataAtualSenha <= current_date - 90")
    List<Usuario> usuarioSenhaVencida();
}
