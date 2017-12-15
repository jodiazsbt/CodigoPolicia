package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_TITULO;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_USUARIO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 13/12/2017.
 */

public class Rutinas_USUARIO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_USUARIO(Context context) {
        this.context = context;
    }

    public boolean crearUsuario(Tabla_USUARIO usuario) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("CONSECUTIVO", usuario.CONSECUTIVO);
        parameters.put("FISICA", usuario.FISICA);
        parameters.put("FUNCIONARIO", usuario.FUNCIONARIO);
        parameters.put("GRADO", usuario.GRADO);
        parameters.put("IDENTIFICACION", usuario.IDENTIFICACION);
        parameters.put("PLACA", usuario.PLACA);
        parameters.put("UNDECONSECUTIVO", usuario.UNDECONSECUTIVO);
        parameters.put("UNDEFUERZA", usuario.UNDEFUERZA);
        parameters.put("UNDELABORA", usuario.UNDELABORA);
        parameters.put("UNIDAD", usuario.UNIDAD);
        parameters.put("VERIFICA", usuario.VERIFICA);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'USUARIO'", null, parameters);
        DB.close();
        return id > 0;
    }

    public boolean existeUsuario(String Placa) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Placa + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM 'USUARIO' WHERE PLACA=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public String usuarioID(String Placa) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Placa + ""};

        Cursor cursor = DB.rawQuery("SELECT ID FROM 'USUARIO' WHERE PLACA=?;", parameters);

        String ID = "0";
        while (cursor.moveToNext()) {
            ID = cursor.getString(0);
        }
        cursor.close();
        DB.close();
        return ID;
    }
}
