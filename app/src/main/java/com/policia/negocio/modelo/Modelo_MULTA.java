package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Modelo_MULTA {

    public Modelo_MULTA(String ID, String Nivel, String Multa, double Valor) {


        this.Nivel = Nivel;
        this.Multa = Multa;
        this.ID = ID;
        this.Valor = Valor;
    }

    public String ID;
    public String Multa;
    public String Nivel;
    public double Valor;
}