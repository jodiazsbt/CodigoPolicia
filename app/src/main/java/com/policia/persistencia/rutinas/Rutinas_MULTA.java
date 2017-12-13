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

    public ArrayList<Modelo_MULTA> Libros(String Idioma){
        DB = new SQLiteProvider(context).getReadableDatabase();
        /*
        Cursor cursor = DB.rawQuery("SELECT " +
                "MULTA.ID, " +
                "NIVEL.NIVEL_"+Idioma+" NIVEL, " +
                "MULTA.MULTA_"+Idioma+" MULTA " +
                "FROM MULTA " +
                "INNER JOIN NIVEL ON MULTA.NIVEL_ID=NIVEL.ID " +
                "WHERE NIVEL.ID IN (16,17,18);", null);//SOLO MULTAS
        */

        Cursor cursor = DB.rawQuery(
        "SELECT      DISTINCT MA.TIPOMULTA_ID,NI.NIVEL_" + Idioma + ", NC.NUMERAL_" + Idioma +
                " FROM        NUMERAL NC" +
                " INNER JOIN  MULTA MA" +
                " ON MA.TIPOMULTA_ID = NC.ID" +
                " INNER JOIN  NIVEL NI" +
                " ON NI.ID = NC.NIVEL_ID" +
                " ORDER BY    NC.ID, NI.ID;", null);


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
