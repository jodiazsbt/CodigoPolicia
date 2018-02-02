package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Modelo_LIBRO {

    public Modelo_LIBRO(String ID, String Nivel, String Libro, String Recurso) {

        this.Nivel = Nivel;
        this.Libro = Libro;
        this.ID = ID;
        this.Recurso = Recurso;
    }

    public String ID;
    public String Libro;
    public String Nivel;
    public String Recurso;
}