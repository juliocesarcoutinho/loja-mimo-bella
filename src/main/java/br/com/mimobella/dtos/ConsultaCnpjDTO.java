package br.com.mimobella.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConsultaCnpjDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String abertura;
    private String situacao;
    private String tipo;
    private String nome;
    private String fantasia;
    private String porte;
    private String natureza_juridica;
    private List<AtividadeDTO> atividade_principal =new ArrayList<AtividadeDTO>();
    private List<AtividadeDTO> atividade_secundarias =new ArrayList<AtividadeDTO>();
    private List<QsaDTO> qsa = new ArrayList<QsaDTO>();
    private String logradouro;
    private String numero;
    private String complemento;
    private String municipio;
    private String bairro;
    private String uf;
    private String cep;
    private String email;
    private String telefone;
    private String data_situacao;
    private String cnpj;
    private String ultima_atualizacao;
    private String status;
    private String efr;
    private String motivo_situacao;
    private String situacao_especial;
    private String data_situacao_especial;
    private String capital_social;

    @JsonIgnore
    private ExtraDTO extra;

    private BillingDTO billing;













}
