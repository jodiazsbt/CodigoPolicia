package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.modelo.Modelo_DOCUMENTO;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 15/01/2018.
 */

public class Rutinas_DOCUMENTO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_DOCUMENTO(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_DOCUMENTO> Documentos(String Idioma) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "DOCUMENTO_" + Idioma + ", " +
                "URL " +
                "FROM DOCUMENTO;", null);

        ArrayList<Modelo_DOCUMENTO> result = new ArrayList<Modelo_DOCUMENTO>();
        while (cursor.moveToNext()) {
            Modelo_DOCUMENTO documento = new Modelo_DOCUMENTO(
                    cursor.getString(0),//DOCUMENTO
                    cursor.getString(1)//URL
            );
            result.add(documento);
        }
        cursor.close();
        DB.close();
        return result;
    }

}
