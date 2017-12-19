package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_MULTA;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Rutinas_MULTA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_MULTA(Context context) {
        this.context = context;
    }

    public ArrayList<Modelo_MULTA> Multas(String Idioma) {
        DB = new SQLiteProvider(context).getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT DISTINCT  " +
                "MULTA.TIPOMULTA_ID, " +
                "CASE   " +
                "WHEN MULTA.TIPOMULTA_ID IN (1004) THEN 'MULTAS ESPECIALES'  " +
                "WHEN MULTA.TIPOMULTA_ID IN (1820) THEN 'COMPARENDO' " +
                "ELSE REPLACE(UPPER(NIVEL.NIVEL_ESP),':','') END NIVEL_ESP, " +
                "UPPER(NUMERAL.NUMERAL_ESP) NUMERAL_ESP " +
                "FROM MULTA " +
                "INNER JOIN NUMERAL ON MULTA.TIPOMULTA_ID=NUMERAL.ID " +
                "INNER JOIN NIVEL ON NUMERAL.NIVEL_ID=NIVEL.ID;", null);

        ArrayList<Modelo_MULTA> result = new ArrayList<Modelo_MULTA>();
        while (cursor.moveToNext()) {
            Modelo_MULTA multa = new Modelo_MULTA(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            result.add(multa);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
