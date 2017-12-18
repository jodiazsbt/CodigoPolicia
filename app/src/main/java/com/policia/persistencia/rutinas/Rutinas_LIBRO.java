package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Libros.LibrosResultEntry;
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

    public String getUltimaActualizacion() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT MAX(FECHA) FROM 'LIBRO';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }
        maxFecha = "01.12.2017";

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public boolean updateRecord(LibrosResultEntry lre) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("LIBRO_ESP", lre.NombreLibro);
            contentValues.put("LIBRO_ENG", lre.NombreLibro);
            contentValues.put("VIGENTE",lre.Vigente_Libro);
            contentValues.put("NIVEL_ID",lre.Id_Nivel_Libro);
            contentValues.put("FECHA",lre.Fecha_Libro);
            DB.update("LIBRO", contentValues, "ID" + " = " + lre.ID_Libro, null);
            DB.close();
        } catch (Exception e) {
            return false;
        }
        return true;
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
