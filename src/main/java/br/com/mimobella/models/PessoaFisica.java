package br.com.mimobella.models;

import br.com.mimobella.enums.TipoPessoa;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaFisica extends Pessoa{

    @Column(nullable = false, length = 14)
    @NotNull(message = "O campo CPF é obrigatório")
    @CPF(message = "CPF informado é invalido verifique!")
    private String cpf;

    @Temporal(TemporalType.DATE)
    @Column(length = 10)
    private Date dataNacimento;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TipoPessoa tipoPessoa;

    /*Getters and Setters*/

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNacimento() {
        return dataNacimento;
    }

    public void setDataNacimento(Date dataNacimento) {
        this.dataNacimento = dataNacimento;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }
    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
}
