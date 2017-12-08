package com.policia.negocio.modelo;

/**
 * Created by JORGE on 3/12/2017.
 */

public class Modelo_Busqueda_Articulo {

    public Modelo_Busqueda_Articulo(String CapituloID, int PosicionArticulo, String Libro, String Titulo, String Capitulo, String Articulo) {

        this.Libro = Libro;
        this.Titulo = Titulo;
        this.Capitulo = Capitulo;
        this.Articulo = Articulo;
        this.CapituloID = CapituloID;
        this.PosicionArticulo = PosicionArticulo;
    }

    public String Libro;
    public String Titulo;
    public String Capitulo;
    public String Articulo;
    public String CapituloID;
    public int PosicionArticulo;
}
