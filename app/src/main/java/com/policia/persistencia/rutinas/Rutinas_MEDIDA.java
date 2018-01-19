package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_MEDIDA;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_MEDIDA;

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

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'MEDIDA';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_MEDIDA medida) {
        String[] parameters = new String[]{
                medida.COMPORTAMIENTO_ESP,
                medida.MEDIDA_ESP + "",
                medida.VIGENTE + "",
                medida.NIVEL_ID + "",
                medida.ARTICULO_ID + "",
                medida.COMPORTAMIENTO_ENG + "",
                medida.MEDIDA_ENG + "",
                medida.FECHA + "",
                medida.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'MEDIDA' SET COMPORTAMIENTO_ESP=?,MEDIDA_ESP=?,VIGENTE=?,NIVEL_ID=?,ARTICULO_ID=?,COMPORTAMIENTO_ENG=?,MEDIDA_ENG=?,FECHA=? WHERE ID=?", parameters);
        DB.close();
    }

    public ArrayList<Modelo_MEDIDA> MedidasPorParagrafo(String Idioma, String Paragrafo) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "MEDIDA.ID," +
                "COMPORTAMIENTO_" + Idioma + "," +
                "MEDIDA_" + Idioma + ", " +
                "NIVEL.NIVEL_" + Idioma + " " +
                "FROM MEDIDA " +
                "INNER JOIN NIVEL ON MEDIDA.NIVEL_ID=NIVEL.ID " +
                "WHERE ARTICULO_ID=" + Paragrafo + " " +
                "ORDER BY MEDIDA.ID;", null);

        ArrayList<Modelo_MEDIDA> result = new ArrayList<Modelo_MEDIDA>();
        while (cursor.moveToNext()) {
            Modelo_MEDIDA medida = new Modelo_MEDIDA(
                    cursor.getString(1),//COMPORTAMIENTO
                    cursor.getString(2),//MEDIDA
                    cursor.getString(3)//NIVEL
            );
            result.add(medida);
        }
        cursor.close();
        DB.close();
        return result;
    }

    public ArrayList<Modelo_MEDIDA> ComparendosNumeral(String Idioma, String Numeral) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "NUMERAL.ID, " +
                "MEDIDA.ID, " +
                "MULTA.TIPOMULTA_ID, " +
                "NIVEL.NIVEL_" + Idioma + ", " +
                "MEDIDA.COMPORTAMIENTO_" + Idioma + "," +
                "MEDIDA.MEDIDA_" + Idioma + ", " +
                "CASE " +
                "WHEN MULTA.TIPOMULTA_ID IN (1004,1820) THEN 0 " +
                "WHEN MULTA.TIPOMULTA_ID IN (1000) THEN UVT.VALOR*4 " +
                "WHEN MULTA.TIPOMULTA_ID IN (1001) THEN UVT.VALOR*8 " +
                "WHEN MULTA.TIPOMULTA_ID IN (1002) THEN UVT.VALOR*16 " +
                "WHEN MULTA.TIPOMULTA_ID IN (1003) THEN UVT.VALOR*32 END VALOR " +
                "FROM NUMERAL " +
                "INNER JOIN MULTA ON MULTA.NUMERAL_ID=NUMERAL.ID " +
                "INNER JOIN MEDIDA ON MULTA.MEDIDA_ID=MEDIDA.ID " +
                "INNER JOIN NIVEL ON MEDIDA.NIVEL_ID=NIVEL.ID " +
                "INNER JOIN (SELECT UVT.VALOR FROM (SELECT MAX(ANIO) ANIO FROM UVT) UVT_MAX INNER JOIN UVT ON UVT_MAX.ANIO=UVT.ANIO) UVT " +
                "WHERE NUMERAL.ID=" + Numeral + ";", null);

        ArrayList<Modelo_MEDIDA> result = new ArrayList<Modelo_MEDIDA>();
        while (cursor.moveToNext()) {
            Modelo_MEDIDA medida = new Modelo_MEDIDA(
                    cursor.getString(3),//NIVEL
                    cursor.getString(4),//COMPORTAMIENTO
                    cursor.getString(5),//MEDIDA
                    cursor.getDouble(6)//VALOR
            );
            result.add(medida);
        }
        cursor.close();
        DB.close();
        return result;
    }

    public int countComparendosNumeral(String Numeral) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT COUNT(MEDIDA.ID) " +
                "FROM NUMERAL " +
                "INNER JOIN MULTA ON MULTA.NUMERAL_ID=NUMERAL.ID " +
                "INNER JOIN MEDIDA ON MULTA.MEDIDA_ID=MEDIDA.ID " +
                "INNER JOIN NIVEL ON MEDIDA.NIVEL_ID=NIVEL.ID " +
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
