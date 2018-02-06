package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_LIBRO;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_LIBRO;

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

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM LIBRO;", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_LIBRO libro) {
        String[] parameters = new String[]{
                libro.LIBRO_ESP,
                libro.VIGENTE + "",
                libro.NIVEL_ID + "",
                libro.FECHA + "",
                libro.LIBRO_ENG + "",
                libro.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'LIBRO' SET LIBRO_ESP=?,VIGENTE=?,NIVEL_ID=?,FECHA=?,LIBRO_ENG=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM LIBRO WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_LIBRO libro) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", libro.ID);
        parameters.put("LIBRO_ESP", libro.LIBRO_ESP);
        parameters.put("VIGENTE", libro.VIGENTE);
        parameters.put("NIVEL_ID", libro.NIVEL_ID);
        parameters.put("FECHA", libro.FECHA);
        parameters.put("LIBRO_ENG", libro.LIBRO_ENG);
        parameters.put("RECURSO_ID", libro.RECURSO_ID);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'LIBRO'", null, parameters);
        DB.close();
        return id > 0;
    }

    public ArrayList<Modelo_LIBRO> Libros(String Idioma) {
        DB = new SQLiteProvider(context).getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT " +
                "LIBRO.ID, " +
                "NIVEL.NIVEL_" + Idioma + " NIVEL, " +
                "LIBRO.LIBRO_" + Idioma + " LIBRO, " +
                "RECURSO_ID " +
                "FROM LIBRO " +
                "INNER JOIN NIVEL ON LIBRO.NIVEL_ID=NIVEL.ID " +
                "WHERE NIVEL.ID IN (16,17,18);", null);//SOLO LIBROS

        ArrayList<Modelo_LIBRO> result = new ArrayList<Modelo_LIBRO>();
        while (cursor.moveToNext()) {
            Modelo_LIBRO libro = new Modelo_LIBRO(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            result.add(libro);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
