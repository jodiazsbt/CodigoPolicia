package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_ACCION;

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

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'ACCION';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_ACCION accion) {
        String[] parameters = new String[]{
                accion.ACCION_ESP,
                accion.ACCION_ENG + "",
                accion.FECHA + "",
                accion.VIGENTE + "",
                accion.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'ACCION' SET ACCION_ESP=?,ACCION_ENG=?,FECHA=?,VIGENTE=? WHERE ID=?", parameters);
        DB.close();
    }
}
