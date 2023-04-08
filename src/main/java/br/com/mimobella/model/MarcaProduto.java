package br.com.mimobella.model;

import org.aspectj.bridge.IMessage;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "marca_produto")
@SequenceGenerator(name = "seq_marca_produto", sequenceName = "seq_marca_produto", allocationSize = 1, initialValue = 1)
public class MarcaProduto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_marca_produto")
    private Long id;
    @Column(nullable = false, length = 120)
    @NotNull(message = "O campo descrição é obrigatório")
    private String nomeDescricao;

    /* ============================================================================================================== */

    /* Getter and Setter */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return nomeDescricao;
    }

    public void setDescricao(String descricao) {
        this.nomeDescricao = descricao;
    }

    /*Equal and hash code*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarcaProduto that = (MarcaProduto) o;
        return Objects.equals(id, that.id) && Objects.equals(nomeDescricao, that.nomeDescricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeDescricao);
    }
}
