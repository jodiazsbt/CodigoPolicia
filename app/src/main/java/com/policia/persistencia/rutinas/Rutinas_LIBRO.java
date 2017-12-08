package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_LIBRO;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Rutinas_LIBRO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_LIBRO(Context context) {
        this.context = context;
    }

    public ArrayList<Modelo_LIBRO> Libros(String Idioma){
        DB = new SQLiteProvider(context).getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT " +
                "LIBRO.ID, " +
                "NIVEL.NIVEL_"+Idioma+" NIVEL, " +
                "LIBRO.LIBRO_"+Idioma+" LIBRO " +
                "FROM LIBRO " +
                "INNER JOIN NIVEL ON LIBRO.NIVEL_ID=NIVEL.ID " +
                "WHERE NIVEL.ID IN (16,17,18);", null);//SOLO LIBROS

        ArrayList<Modelo_LIBRO> result = new ArrayList<Modelo_LIBRO>();
        while (cursor.moveToNext()) {
            Modelo_LIBRO libro = new Modelo_LIBRO(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            result.add(libro);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
