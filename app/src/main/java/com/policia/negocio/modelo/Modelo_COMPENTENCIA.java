package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 10/01/2018.
 */

public class Modelo_COMPENTENCIA {

    public Modelo_COMPENTENCIA(String ID,String Competencia, String Instancia) {

        this.Competencia = Competencia;
        this.Instancia = Instancia;
        this.ID = ID;
    }

    public String Competencia;
    public String Instancia;
    public String ID;
}
