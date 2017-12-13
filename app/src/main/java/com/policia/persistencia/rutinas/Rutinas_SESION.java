package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_SESION;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_SESION;
import com.policia.persistencia.tablas.Tabla_USUARIO;

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
                "IDIOMA.IDIOMA, " +
                "PREFERENCIA.IDIOMA_CODIGO " +
                "FROM USUARIO " +
                "INNER JOIN SESION ON USUARIO.ID=SESION.USUARIO_ID " +
                "INNER JOIN PREFERENCIA ON USUARIO.ID=PREFERENCIA.USUARIO_ID " +
                "INNER JOIN IDIOMA ON PREFERENCIA.IDIOMA_CODIGO=IDIOMA.CODIGO;", null);

        Modelo_SESION sesion = null;
        while (cursor.moveToNext()) {
            sesion = new Modelo_SESION();
            sesion.setUsuario(cursor.getString(0));
            sesion.setIdiomaNombre(cursor.getString(1));
            sesion.setIdiomaCodigo(cursor.getString(2));
        }
        cursor.close();
        DB.close();
        return sesion;
    }

    public boolean crearSesion(Tabla_SESION sesion) {

        return true;
    }

    public boolean existeSesionUsuario(String Placa) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Placa + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM USUARIO INNER JOIN SESION ON USUARIO.ID=SESION.USUARIO_ID WHERE USUARIO.PLACA=?", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }
}
