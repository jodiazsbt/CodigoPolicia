package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Modelo_CAPITULO_MULTA {

    public Modelo_CAPITULO_MULTA(String ID, String Nivel, String Capitulo) {

        this.Nivel = Nivel;
        this.Capitulo = Capitulo;
        this.ID = ID;
    }

    public String ID;
    public String Capitulo;
    public String Nivel;
}
