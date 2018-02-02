package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_AVATAR;

/**
 * Created by 1085253556 on 31/01/2018.
 */

public class Rutinas_AVATAR {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_AVATAR(Context context) {

        this.context = context;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'AVATAR';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_AVATAR avatar) {
        String[] parameters = new String[]{
                avatar.FECHA + "",
                avatar.UBICACION + "",
                avatar.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'AVATAR' SET FECHA=?,UBICACION=? WHERE ID=?", parameters);
        DB.close();
    }

    public Tabla_AVATAR read(String nombre) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                nombre};

        Cursor cursor = DB.rawQuery("SELECT ID,AVATAR,FECHA,UBICACION FROM 'AVATAR' WHERE AVATAR=?;", parameters);

        Tabla_AVATAR avatar = null;
        while (cursor.moveToNext()) {
            avatar = new Tabla_AVATAR();
            avatar.ID = cursor.getString(0);
            avatar.AVATAR = cursor.getString(1);
            avatar.FECHA = cursor.getString(2);
            avatar.UBICACION = cursor.getString(3);
        }

        cursor.close();
        DB.close();
        return avatar;
    }
}
