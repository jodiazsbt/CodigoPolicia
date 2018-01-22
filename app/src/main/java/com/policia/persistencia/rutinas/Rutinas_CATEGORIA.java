package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_CATEGORIA;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Rutinas_CATEGORIA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_CATEGORIA(Context context) {
        this.context = context;
    }

    public ArrayList<Modelo_CATEGORIA> CategoriaPorTipoMulta(String Idioma, String TipoMulta) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                TipoMulta + ""};

        Cursor cursor = DB.rawQuery("SELECT DISTINCT " +
                "B.ID, B.CATEGORIA_ESP,B.RECURSO_ID " +
                "FROM MULTA A " +
                "INNER JOIN " +
                "CATEGORIA B ON A.CATEGORIA_ID = B.ID " +
                "WHERE A.TIPOMULTA_ID=? " +
                "ORDER BY B.ID ASC;", parameters);

        ArrayList<Modelo_CATEGORIA> result = new ArrayList<Modelo_CATEGORIA>();
        while (cursor.moveToNext()) {
            Modelo_CATEGORIA categoria = new Modelo_CATEGORIA(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            result.add(categoria);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
