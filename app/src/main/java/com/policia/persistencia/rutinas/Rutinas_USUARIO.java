package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_USUARIO;

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
        parameters.put("IDENTIFICACION", usuario.IDENTIFICACION);
        parameters.put("SIGLAPAPA", usuario.SIGLAPAPA);
        parameters.put("SIGLAFISICA", usuario.SIGLAFISICA);
        parameters.put("GRADOALFABETICO", usuario.GRADOALFABETICO);
        parameters.put("APELLIDOS", usuario.APELLIDOS);
        parameters.put("NOMBRES", usuario.NOMBRES);
        parameters.put("SITUACIONLABORAL", usuario.SITUACIONLABORAL);
        parameters.put("NOMBREGRADO", usuario.NOMBREGRADO);
        parameters.put("CARGOACTUAL", usuario.CARGOACTUAL);
        parameters.put("CONSECUTIVO", usuario.CONSECUTIVO);
        parameters.put("UNDECONSECUTIVO", usuario.UNDECONSECUTIVO);
        parameters.put("UNDEFUERZA", usuario.UNDEFUERZA);
        parameters.put("FUNCIONARIO", usuario.FUNCIONARIO);
        parameters.put("UNIDAD", usuario.UNIDAD);
        parameters.put("PLACA", usuario.PLACA);
        parameters.put("UNDECONSECUTIVOLABORANDO", usuario.UNDECONSECUTIVOLABORANDO);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'USUARIO'", null, parameters);
        DB.close();
        return id > 0;
    }

    public boolean existeUsuario(String IDENTIFICACION) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                IDENTIFICACION + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM 'USUARIO' WHERE IDENTIFICACION=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public String usuarioID(String IDENTIFICACION) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                IDENTIFICACION + ""};

        Cursor cursor = DB.rawQuery("SELECT ID FROM 'USUARIO' WHERE IDENTIFICACION=?;", parameters);

        String ID = "0";
        while (cursor.moveToNext()) {
            ID = cursor.getString(0);
        }
        cursor.close();
        DB.close();
        return ID;
    }
}
