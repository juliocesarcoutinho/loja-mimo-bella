package br.com.mimobella.models;

import br.com.mimobella.enums.TipoPessoa;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa{

    @Column(nullable = false, length = 18)
    @NotNull(message = "O campo CNPJ é obrigatório")
    @CNPJ(message = "CNPJ informado está invalido verifique!")
    private String cnpj;
    @Column(length = 15)
    private String inscEstadual;
    private String inscMunicipal;
    @Column(length = 120)
    private String nomeFantasia;
    @Column(nullable = false, length = 120)
    @NotNull(message = "O campo Razão social é obrigatório")
    private String razaoSocial;
    private String categoria;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TipoPessoa tipoPessoa;


    /*Getters and Setters*/

    public String getCnpj() {
        return cnpj;
    }

        public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getInscMunicipal() {
        return inscMunicipal;
    }

    public void setInscMunicipal(String inscMunicipal) {
        this.inscMunicipal = inscMunicipal;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
}
