package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_MEDIDA;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 17/12/2017.
 */

public class Rutinas_MEDIDA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_MEDIDA(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_MEDIDA> MedidasPorParagrafo(String Idioma, String Paragrafo) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "MEDIDA.ID," +
                "COMPORTAMIENTO_" + Idioma + "," +
                "MEDIDA_" + Idioma + " " +
                "FROM MEDIDA " +
                "WHERE ARTICULO_ID=" + Paragrafo + ";", null);

        ArrayList<Modelo_MEDIDA> result = new ArrayList<Modelo_MEDIDA>();
        while (cursor.moveToNext()) {
            Modelo_MEDIDA medida = new Modelo_MEDIDA(
                    cursor.getString(0),//COMPORTAMIENTO
                    cursor.getString(1)//MEDIDA
            );
            result.add(medida);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
