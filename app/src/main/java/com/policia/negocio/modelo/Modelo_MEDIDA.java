package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 17/12/2017.
 */

public class Modelo_MEDIDA {

    public Modelo_MEDIDA(String Comportamiento, String Medida, String Nivel) {

        this.Comportamiento = Comportamiento;
        this.Medida = Medida;
        this.Nivel = Nivel;
        this.Valor = 0;
    }

    public Modelo_MEDIDA(String Nivel, String Comportamiento, String Medida, double Valor) {

        this.Comportamiento = Comportamiento;
        this.Medida = Medida;
        this.Nivel = Nivel;
        this.Valor = Valor;
    }

    public String Comportamiento;
    public String Medida;
    public String Nivel;
    public double Valor;
}
