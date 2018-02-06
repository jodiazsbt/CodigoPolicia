package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_NIVEL;

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

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'NIVEL';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_NIVEL nivel) {
        String[] parameters = new String[]{
                nivel.NIVEL_ESP,
                nivel.VIGENTE + "",
                nivel.FECHA + "",
                nivel.NIVEL_ENG + "",
                nivel.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'NIVEL' SET NIVEL_ESP=?,VIGENTE=?,FECHA=?,NIVEL_ENG=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM NIVEL WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_NIVEL nivel) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", nivel.ID);
        parameters.put("NIVEL_ESP", nivel.NIVEL_ESP);
        parameters.put("VIGENTE", nivel.VIGENTE);
        parameters.put("FECHA", nivel.FECHA);
        parameters.put("NIVEL_ENG", nivel.NIVEL_ENG);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'NIVEL'", null, parameters);
        DB.close();
        return id > 0;
    }

}
