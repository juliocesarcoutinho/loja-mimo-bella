package br.com.mimobella.model;

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
    private String cpf;

    @Temporal(TemporalType.DATE)
    @Column(length = 10)
    private Date dataNacimento;

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
}
