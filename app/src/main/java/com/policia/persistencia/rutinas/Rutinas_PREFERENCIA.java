package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_PREFERENCIA;
import com.policia.persistencia.tablas.Tabla_SESION;

/**
 * Created by 1085253556 on 11/12/2017.
 */

public class Rutinas_PREFERENCIA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_PREFERENCIA(Context context) {

        this.context = context;
    }

    public void update(Tabla_PREFERENCIA preferencia) {
        String[] parameters = new String[]{
                preferencia.IDIOMA_CODIGO,
                preferencia.USUARIO_ID
        };

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'PREFERENCIA' SET IDIOMA_CODIGO=? WHERE USUARIO_ID=?", parameters);
        DB.close();
    }

    public Tabla_PREFERENCIA read(String usuarioId) {

        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parametros = new String[]{usuarioId};

        Cursor cursor = DB.rawQuery("SELECT * FROM 'PREFERENCIA' WHERE ID=?;", parametros);

        Tabla_PREFERENCIA preferencia = null;
        while (cursor.moveToNext()) {
            preferencia = new Tabla_PREFERENCIA();
            preferencia.USUARIO_ID = cursor.getString(0);
            preferencia.IDIOMA_CODIGO = cursor.getString(1);
        }
        cursor.close();
        DB.close();
        return preferencia;
    }

    public boolean crearPreferencia(Tabla_PREFERENCIA preferencia) {

        return true;
    }

    public boolean existePreferenciaUsuario(String Placa) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Placa + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM USUARIO INNER JOIN PREFERENCIA ON USUARIO.ID=PREFERENCIA.USUARIO_ID WHERE USUARIO.PLACA=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }
}
