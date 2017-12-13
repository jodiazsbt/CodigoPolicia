package com.policia.persistencia.rutinas;

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

        return true;
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
