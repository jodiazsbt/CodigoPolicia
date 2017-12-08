package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 30/11/2017.
 */

public class Modelo_ARTICULO {

    public Modelo_ARTICULO(
            String ID,
            String Nivel,
            String Titulo,
            String Capitulo_Nivel,
            String Capitulo_Descripcion,
            String Articulo_Nivel,
            String Articulo_Titulo,
            String Articulo_Descripcion) {

        this.ID = ID;
        this.Nivel = Nivel;
        this.Titulo = Titulo;
        this.Capitulo_Nivel = Capitulo_Nivel;
        this.Capitulo_Descripcion = Capitulo_Descripcion;
        this.Articulo_Nivel = Articulo_Nivel;
        this.Articulo_Titulo = Articulo_Titulo;
        this.Articulo_Descripcion = Articulo_Descripcion;
    }

    public String ID;
    public String Nivel;
    public String Titulo;
    public String Capitulo_Nivel;
    public String Capitulo_Descripcion;
    public String Articulo_Nivel;
    public String Articulo_Titulo;
    public String Articulo_Descripcion;
}
