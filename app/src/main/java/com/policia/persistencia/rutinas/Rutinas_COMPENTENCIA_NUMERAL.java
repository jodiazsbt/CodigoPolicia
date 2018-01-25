package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_COMPETENCIA_NUMERAL;

/**
 * Created by 1085253556 on 23/01/2018.
 */

public class Rutinas_COMPENTENCIA_NUMERAL {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_COMPENTENCIA_NUMERAL(Context context) {

        this.context = context;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'COMPETENCIA_NUMERAL';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_COMPETENCIA_NUMERAL competencia_numeral) {
        String[] parameters = new String[]{
                competencia_numeral.NUMERAL_ID,
                competencia_numeral.COMPETENCIA_ID + "",
                competencia_numeral.VIGENTE + "",
                competencia_numeral.FECHA + "",
                competencia_numeral.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'COMPETENCIA_NUMERAL' SET NUMERAL_ID=?,COMPETENCIA_ID=?,VIGENTE=?,FECHA=? WHERE ID=?", parameters);
        DB.close();
    }

}
