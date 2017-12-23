package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;

/**
 * Created by 1085253556 on 23/12/2017.
 */

public class Rutinas_NIVEL {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_NIVEL(Context context) {

        this.context = context;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT MAX(FECHA) FROM 'NIVEL';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }
        maxFecha = "01.12.2017";

        cursor.close();
        DB.close();
        return maxFecha;
    }

}
