package br.com.mimobella.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class QsaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String qual;
    private String pais_origem;
    private String nome_rep_legal;
    private String qual_rep_legal;

}
