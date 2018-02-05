package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_TITULO;
import com.policia.persistencia.tablas.Tabla_UVT;

/**
 * Created by 1085253556 on 23/01/2018.
 */

public class Rutinas_UVT {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_UVT(Context context) {

        this.context = context;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'UVT';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_UVT UVT) {
        String[] parameters = new String[]{
                UVT.VALOR,
                UVT.ACTIVO + "",
                UVT.FECHA + "",
                UVT.ANIO + "",
                UVT.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'UVT' SET VALOR=?,ACTIVO=?,FECHA=?,ANIO=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM UVT WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_UVT UVT) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", UVT.ID);
        parameters.put("VALOR", UVT.VALOR);
        parameters.put("ACTIVO", UVT.ACTIVO);
        parameters.put("FECHA", UVT.FECHA);
        parameters.put("ANIO", UVT.ANIO);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'UVT'", null, parameters);
        DB.close();
        return id > 0;
    }
}
