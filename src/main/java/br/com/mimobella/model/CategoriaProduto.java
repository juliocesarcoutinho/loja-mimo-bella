package br.com.mimobella.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "categoria_produto")
@SequenceGenerator(name = "seq_categoria_produto", sequenceName = "seq_categoria_produto",
        allocationSize = 1, initialValue = 1)
public class CategoriaProduto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria_produto")
    private Long id;

    @Column(nullable = false, length = 120)
    private String nomeDescricao;


    /*Getter and Setters*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDescricao() {
        return nomeDescricao;
    }

    public void setNomeDescricao(String nomeDescricao) {
        this.nomeDescricao = nomeDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriaProduto that = (CategoriaProduto) o;

        if (!id.equals(that.id)) return false;
        return Objects.equals(nomeDescricao, that.nomeDescricao);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (nomeDescricao != null ? nomeDescricao.hashCode() : 0);
        return result;
    }

}
