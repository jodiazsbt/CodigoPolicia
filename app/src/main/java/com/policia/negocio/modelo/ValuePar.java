package com.policia.negocio.modelo;

/**
 * Created by JORGE on 26/11/2017.
 */

public class ValuePar {

    private String label;

    private String text;

    public ValuePar(String label, String text) {
        this.label=label;
        this.text=text;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
