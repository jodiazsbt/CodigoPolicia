package com.policia.negocio.modelo;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Modelo_TITULO_MULTA {

    public Modelo_TITULO_MULTA(String ID, String Nivel, String Titulo) {

        this.Nivel = Nivel;
        this.Titulo = Titulo;
        this.ID = ID;
    }

    public String ID;
    public String Titulo;
    public String Nivel;
}
