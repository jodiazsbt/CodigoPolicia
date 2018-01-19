package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_COMPENTENCIA;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 10/01/2018.
 */

public class Rutinas_COMPETENCIA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_COMPETENCIA(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_COMPENTENCIA> competenciasPorNumeral(String Idioma, String Numeral) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT DISTINCT " +
                "COMPETENCIA.ID, " +
                "COMPETENCIA.COMPETENCIA_" + Idioma + "," +
                "COMPETENCIA.INSTANCIA_" + Idioma + " " +
                "FROM NUMERAL " +
                "INNER JOIN COMPETENCIA_NUMERAL ON NUMERAL.ID=COMPETENCIA_NUMERAL.NUMERAL_ID " +
                "INNER JOIN COMPETENCIA ON COMPETENCIA_NUMERAL.COMPETENCIA_ID=COMPETENCIA.ID " +
                "WHERE NUMERAL.ID=" + Numeral + ";", null);

        ArrayList<Modelo_COMPENTENCIA> result = new ArrayList<Modelo_COMPENTENCIA>();
        while (cursor.moveToNext()) {
            Modelo_COMPENTENCIA compentencia = new Modelo_COMPENTENCIA(
                    cursor.getString(0),//ID
                    cursor.getString(1),//COMPETENCIA
                    cursor.getString(2)//INSTANCIA
            );
            result.add(compentencia);
        }
        cursor.close();
        DB.close();
        return result;
    }


    public int countCompetenciasPorNumeral(String Numeral) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT COUNT(COMPETENCIA.ID) " +
                "FROM NUMERAL " +
                "INNER JOIN COMPETENCIA_NUMERAL ON NUMERAL.ID=COMPETENCIA_NUMERAL.NUMERAL_ID " +
                "INNER JOIN COMPETENCIA ON COMPETENCIA_NUMERAL.COMPETENCIA_ID=COMPETENCIA.ID " +
                "WHERE NUMERAL.ID=" + Numeral + ";", null);

        int result = 0;
        while (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
