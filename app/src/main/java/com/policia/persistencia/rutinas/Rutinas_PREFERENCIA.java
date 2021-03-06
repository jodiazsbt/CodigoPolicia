package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_PREFERENCIA;

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
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("USUARIO_ID", preferencia.USUARIO_ID);
        parameters.put("IDIOMA_CODIGO", preferencia.IDIOMA_CODIGO);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'PREFERENCIA'", null, parameters);
        DB.close();
        return id > 0;
    }

    public boolean existePreferenciaUsuario(String usuarioId) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                usuarioId + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM PREFERENCIA WHERE USUARIO_ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }
}
