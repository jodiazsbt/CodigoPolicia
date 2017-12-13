package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.policia.negocio.modelo.Modelo_TITULO_MULTA;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Rutinas_TITULO_MULTA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_TITULO_MULTA(Context context) {
        this.context = context;
    }

    public ArrayList<Modelo_TITULO_MULTA> TitulosPorLibro(String Idioma, String Libro) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Libro + ""};



        Cursor cursor = DB.rawQuery("SELECT DISTINCT CATEGORIA.ID,CATEGORIA.CATEGORIA_" + Idioma +
        " FROM MULTA" +
        " INNER JOIN CATEGORIA ON MULTA.CATEGORIA_ID=CATEGORIA.ID" +
        " WHERE MULTA.TIPOMULTA_ID=?;", parameters);


        /*
        Cursor cursor = DB.rawQuery("SELECT " +
                "TITULO.ID, " +
                "NIVEL.NIVEL_" + Idioma + " NIVEL, " +
                "TITULO.TITULO_" + Idioma + " TITULO " +
                "FROM TITULO " +
                "INNER JOIN NIVEL ON TITULO.NIVEL_ID=NIVEL.ID " +
                "WHERE TITULO.LIBRO_ID=?;", parameters);
*/
        ArrayList<Modelo_TITULO_MULTA> result = new ArrayList<Modelo_TITULO_MULTA>();
        while (cursor.moveToNext()) {
            Modelo_TITULO_MULTA titulo = new Modelo_TITULO_MULTA(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            result.add(titulo);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
