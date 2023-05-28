package br.com.mimobella.dtos;

import lombok.Data;

import java.io.Serializable;


public class BillingDTO implements Serializable {

    private Boolean free;
    private Boolean database;

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Boolean getDatabase() {
        return database;
    }

    public void setDatabase(Boolean database) {
        this.database = database;
    }
}
