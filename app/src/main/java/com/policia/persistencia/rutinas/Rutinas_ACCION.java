package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;

/**
 * Created by 1085253556 on 9/01/2018.
 */

public class Rutinas_ACCION {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_ACCION(Context context) {

        this.context = context;
    }

    public String accionCiudadano(String idioma) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "ACCION_" + idioma + " " +
                "FROM 'ACCION' WHERE ID=1000;", null);

        String accion = null;
        while (cursor.moveToNext()) {
            accion = cursor.getString(0);
        }
        cursor.close();
        DB.close();
        return accion;
    }

    public String accionPolicia(String idioma) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "ACCION_" + idioma + " " +
                "FROM 'ACCION' WHERE ID=1001;", null);

        String accion = null;
        while (cursor.moveToNext()) {
            accion = cursor.getString(0);
        }
        cursor.close();
        DB.close();
        return accion;
    }
}
