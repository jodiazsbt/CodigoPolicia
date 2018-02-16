package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_SESION;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_SESION;

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
                "USUARIO.APELLIDOS || '-' || USUARIO.NOMBRES, " +
                "USUARIO.SIGLAFISICA, " +
                "IDIOMA.IDIOMA, " +
                "IDIOMA.CODIGO, " +
                "IDIOMA.CODIGO2 " +
                "FROM USUARIO " +
                "INNER JOIN SESION ON USUARIO.ID=SESION.USUARIO_ID " +
                "INNER JOIN PREFERENCIA ON USUARIO.ID=PREFERENCIA.USUARIO_ID " +
                "INNER JOIN IDIOMA ON PREFERENCIA.IDIOMA_CODIGO=IDIOMA.CODIGO;", null);

        Modelo_SESION sesion = null;
        while (cursor.moveToNext()) {
            sesion = new Modelo_SESION();
            sesion.setUsuario(cursor.getString(0));
            sesion.setFuncionario(cursor.getString(1));
            sesion.setFisica(cursor.getString(2));
            sesion.setIdiomaNombre(cursor.getString(3));
            sesion.setIdiomaLargo(cursor.getString(4));
            sesion.setIdiomaCorto(cursor.getString(5));
        }
        cursor.close();
        DB.close();
        return sesion;
    }

    public void borrarSesiones() {
        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.delete("SESION", null, null);
        DB.close();
    }

    public boolean crearSesion(Tabla_SESION sesion) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("USUARIO_ID", sesion.USUARIO_ID);
        parameters.put("FECHA", sesion.FECHA + "");

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'SESION'", null, parameters);
        DB.close();
        return id > 0;
    }

    public boolean exists(String usuarioId) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                usuarioId + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM SESION WHERE USUARIO_ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }
}
