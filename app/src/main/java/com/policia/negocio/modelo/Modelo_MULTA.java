package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Modelo_MULTA {

    public Modelo_MULTA(String ID, String Nivel, String Multa) {


        this.Nivel = Nivel;
        this.Multa = Multa;
        this.ID = ID;
    }

    public String ID;
    public String Multa;
    public String Nivel;
}