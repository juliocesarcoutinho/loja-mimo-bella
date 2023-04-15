package br.com.mimobella.dtos;

import java.io.Serializable;

public class ObjetoErroDTO implements Serializable {
    private String error;
    private String cod;


    /*Getters and Setters*/
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}
