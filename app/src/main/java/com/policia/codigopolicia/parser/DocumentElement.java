package com.policia.codigopolicia.parser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DocumentElement implements Serializable {

    @SerializedName("Table")
    @Expose
    private Table table;
    private final static long serialVersionUID = -297659052080051123L;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}