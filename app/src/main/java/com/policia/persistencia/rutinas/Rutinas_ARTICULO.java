package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.modelo.Modelo_ARTICULO_MULTA;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 30/11/2017.
 */

public class Rutinas_ARTICULO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_ARTICULO(Context context) {

        this.context = context;
    }

    public int CantidadArticulosPorCapitulo(String Capitulo) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Capitulo + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) CANTIDAD FROM 'ARTICULO' WHERE CAPITULO_ID=?;", parameters);
        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad;
    }

    public ArrayList<Modelo_ARTICULO> ArticulosPorCapitulo(String Idioma, String Capitulo, int position) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM ( " +
                "SELECT " +
                "ARTICULO.ID , " +
                "NIVEL.NIVEL_" + Idioma + " NIVEL, " +
                "NIVEL_TITULO.NIVEL_" + Idioma + " TITULO , " +
                "NIVEL_CAPITULO.NIVEL_" + Idioma + " CAPITULO , " +
                "CAPITULO.CAPITULO_" + Idioma + " CAPITULO , " +
                "NIVEL_ARTICULO.NIVEL_" + Idioma + " ARTICULO , " +
                "ARTICULO.TITULO_" + Idioma + " TITULO, " +
                "ARTICULO.ARTICULO_" + Idioma + " ARTICULO " +
                "FROM NIVEL " +
                "INNER JOIN LIBRO ON NIVEL.ID=LIBRO.NIVEL_ID " +
                "INNER JOIN TITULO ON LIBRO.ID=TITULO.LIBRO_ID " +
                "INNER JOIN NIVEL NIVEL_TITULO ON TITULO.NIVEL_ID=NIVEL_TITULO.ID " +
                "INNER JOIN CAPITULO ON TITULO.ID=CAPITULO.TITULO_ID " +
                "INNER JOIN NIVEL NIVEL_CAPITULO ON CAPITULO.NIVEL_ID=NIVEL_CAPITULO.ID " +
                "INNER JOIN ARTICULO ON CAPITULO.ID=ARTICULO.CAPITULO_ID " +
                "INNER JOIN NIVEL NIVEL_ARTICULO ON ARTICULO.NIVEL_ID=NIVEL_ARTICULO.ID " +
                "WHERE ARTICULO.CAPITULO_ID=" + Capitulo + " ) Q " +
                "WHERE ( SELECT COUNT(*)+1 FROM ARTICULO WHERE CAPITULO_ID=" + Capitulo + " AND ID <Q.ID ) =" + position + " ;", null);

        ArrayList<Modelo_ARTICULO> result = new ArrayList<Modelo_ARTICULO>();
        while (cursor.moveToNext()) {
            Modelo_ARTICULO articulo = new Modelo_ARTICULO(
                    cursor.getString(0),//ID
                    cursor.getString(1),//NIVEL
                    cursor.getString(2),//TITULO
                    cursor.getString(3),//CAPITULO
                    cursor.getString(4),//CAPITULO
                    cursor.getString(5),//ARTICULO
                    cursor.getString(6),//TITULO
                    cursor.getString(7)//DESCRIPCION
            );
            result.add(articulo);
        }
        cursor.close();
        DB.close();
        return result;
    }

    public int CantidadArticulosPorMultaCategoria(String Multa, String Categoria) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Multa + "",
                Categoria};

        Cursor cursor = DB.rawQuery("SELECT " +
                "COUNT(DISTINCT ARTICULO.ID) " +
                "FROM ARTICULO " +
                "INNER JOIN NUMERAL ON ARTICULO.ID = NUMERAL.ARTICULO_ID " +
                "INNER JOIN MULTA ON MULTA.NUMERAL_ID = NUMERAL.ID " +
                "INNER JOIN CATEGORIA ON MULTA.CATEGORIA_ID = CATEGORIA.ID " +
                "WHERE CATEGORIA.ID = "+Categoria+" AND MULTA.TIPOMULTA_ID = "+Multa+";", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad;
    }

    public ArrayList<Modelo_ARTICULO> ArticulosPorMultaCategoria(String Idioma, String Multa, String Categoria, int position) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM ( " +
                "SELECT DISTINCT " +
                "ARTICULO.ID," +
                "NIVEL_LIBRO.NIVEL_" + Idioma + " NIVEL," +
                "NIVEL_TITULO.NIVEL_" + Idioma + " TITULO," +
                "NIVEL_CAPITULO.NIVEL_" + Idioma + " CAPITULO," +
                "CAPITULO.CAPITULO_" + Idioma + " CAPITULO," +
                "NIVEL_ARTICULO.NIVEL_" + Idioma + " TITULO," +
                "ARTICULO.ARTICULO_" + Idioma + " ARTICULO " +
                "FROM ARTICULO " +
                "INNER JOIN NIVEL NIVEL_ARTICULO ON ARTICULO.NIVEL_ID = NIVEL_ARTICULO.ID " +
                "INNER JOIN NUMERAL ON ARTICULO.ID = NUMERAL.ARTICULO_ID " +
                "INNER JOIN MULTA ON MULTA.NUMERAL_ID = NUMERAL.ID " +
                "INNER JOIN CATEGORIA ON CATEGORIA.ID = MULTA.CATEGORIA_ID " +
                "INNER JOIN CAPITULO ON ARTICULO.CAPITULO_ID = CAPITULO.ID " +
                "INNER JOIN NIVEL NIVEL_CAPITULO ON CAPITULO.NIVEL_ID = NIVEL_CAPITULO.ID " +
                "INNER JOIN TITULO ON CAPITULO.TITULO_ID = TITULO.ID " +
                "INNER JOIN NIVEL NIVEL_TITULO ON TITULO.NIVEL_ID = NIVEL_TITULO.ID " +
                "INNER JOIN LIBRO ON TITULO.LIBRO_ID = LIBRO.ID " +
                "INNER JOIN NIVEL NIVEL_LIBRO ON LIBRO.NIVEL_ID = NIVEL_LIBRO.ID " +
                "WHERE CATEGORIA.ID = " + Categoria + " AND MULTA.TIPOMULTA_ID = " + Multa + " ) Q " +
                "WHERE (" +
                "SELECT " +
                "COUNT(DISTINCT ARTICULO.ID) + 1 " +
                "FROM ARTICULO " +
                "INNER JOIN NUMERAL ON ARTICULO.ID = NUMERAL.ARTICULO_ID " +
                "INNER JOIN MULTA ON MULTA.NUMERAL_ID = NUMERAL.ID " +
                "INNER JOIN CATEGORIA ON MULTA.CATEGORIA_ID = CATEGORIA.ID " +
                "WHERE CATEGORIA.ID = " + Categoria + " AND MULTA.TIPOMULTA_ID = " + Multa + " AND ARTICULO.ID < Q.ID) = " + position + ";", null);

        ArrayList<Modelo_ARTICULO> result = new ArrayList<Modelo_ARTICULO>();
        while (cursor.moveToNext()) {
            Modelo_ARTICULO articulo = new Modelo_ARTICULO(
                    cursor.getString(0),//ID
                    cursor.getString(1),//NIVEL
                    cursor.getString(2),//TITULO
                    cursor.getString(3),//CAPITULO
                    cursor.getString(4),//CAPITULO
                    cursor.getString(5),//ARTICULO
                    cursor.getString(6),//TITULO
                    cursor.getString(7)//DESCRIPCION
            );
            result.add(articulo);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
