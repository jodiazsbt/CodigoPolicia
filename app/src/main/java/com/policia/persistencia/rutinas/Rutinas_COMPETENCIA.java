package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_COMPENTENCIA;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_COMPETENCIA;

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

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'COMPETENCIA';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_COMPETENCIA competencia) {
        String[] parameters = new String[]{
                competencia.COMPETENCIA_ESP,
                competencia.COMPETENCIA_ENG + "",
                competencia.INSTANCIA_ESP + "",
                competencia.INSTANCIA_ENG + "",
                competencia.VIGENTE + "",
                competencia.FECHA + "",
                competencia.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'COMPETENCIA' SET COMPETENCIA_ESP=?,COMPETENCIA_ENG=?,INSTANCIA_ESP=?,INSTANCIA_ENG=?,VIGENTE=?,FECHA=? WHERE ID=?", parameters);
        DB.close();
    }
}
