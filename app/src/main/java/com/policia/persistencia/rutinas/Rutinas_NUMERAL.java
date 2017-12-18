package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.modelo.Modelo_NUMERAL;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 14/12/2017.
 */

public class Rutinas_NUMERAL {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_NUMERAL(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_NUMERAL> NumeralesPorArticulo(String Idioma, String Articulo) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "NIVEL.NIVEL_" + Idioma + ", " +
                "NUMERAL.NUMERAL_" + Idioma + " " +
                "FROM NUMERAL " +
                "INNER JOIN NIVEL ON NUMERAL.NIVEL_ID=NIVEL.ID " +
                "WHERE ARTICULO_ID=" + Articulo + " " +
                "ORDER BY CAST(NIVEL.NIVEL_ESP AS INT);", null);

        ArrayList<Modelo_NUMERAL> result = new ArrayList<Modelo_NUMERAL>();
        while (cursor.moveToNext()) {
            Modelo_NUMERAL articulo = new Modelo_NUMERAL(
                    cursor.getString(0),//NIVEL
                    cursor.getString(1)//NUMERAL
            );
            result.add(articulo);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
