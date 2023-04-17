package br.com.mimobella.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "nota_item_produto")
@SequenceGenerator(name = "seq_nota_item_produto", sequenceName = "seq_nota_item_produto",
        allocationSize = 1, initialValue = 1)
public class NotaItemProduto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_item_produto")
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Quantidade obrigatório")
    private Double quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "profuto_fk"))
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "nota_fiscal_compra_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_fiscal_compra_fk"))
    private NotaFiscalCompra notaFiscalCompra;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
            name = "empresa_fk"))
    private Pessoa empresa;

    /*Getter and Setters*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public NotaFiscalCompra getNotaFiscalCompra() {
        return notaFiscalCompra;
    }

    public void setNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) {
        this.notaFiscalCompra = notaFiscalCompra;
    }

    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Pessoa empresa) {
        this.empresa = empresa;
    }
}
