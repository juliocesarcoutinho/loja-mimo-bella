package br.com.mimobella.dtos;

import lombok.Data;

import java.io.Serializable;


public class AtividadeDTO implements Serializable {
    private String text;
    private String code;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
