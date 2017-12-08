package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_SESION;
import com.policia.persistencia.conexion.SQLiteProvider;

/**
 * Created by 1085253556 on 7/12/2017.
 */

public class Rutinas_SESION {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_SESION(Context context) {
        this.context = context;
    }

    public Modelo_SESION Sesion() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "USUARIO.ID," +
                "PREFERENCIA.IDIOMA_CODIGO " +
                "FROM USUARIO " +
                "INNER JOIN SESION ON USUARIO.ID=SESION.USUARIO_ID " +
                "INNER JOIN PREFERENCIA ON USUARIO.ID=PREFERENCIA.USUARIO_ID;", null);

        Modelo_SESION sesion = null;
        while (cursor.moveToNext()) {
            sesion = new Modelo_SESION();
            sesion.setUsuario(cursor.getString(0));
            sesion.setIdioma(cursor.getString(1));
        }
        cursor.close();
        DB.close();
        return sesion;
    }
}
