package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.modelo.Modelo_NUMERAL;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_METADATA;
import com.policia.persistencia.tablas.Tabla_NUMERAL;
import com.policia.remote.RemoteClient;

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

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'NUMERAL';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_NUMERAL numeral) {
        String[] parameters = new String[]{
                numeral.NUMERAL_ESP,
                numeral.NUMERAL_ENG + "",
                numeral.VIGENTE + "",
                numeral.NIVEL_ID + "",
                numeral.ARTICULO_ID + "",
                numeral.FECHA + "",
                numeral.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'NUMERAL' SET NUMERAL_ESP=?,NUMERAL_ENG=?,VIGENTE=?,NIVEL_ID=?,ARTICULO_ID=?,FECHA=? WHERE ID=?", parameters);
        DB.close();
    }

    public ArrayList<Modelo_NUMERAL> NumeralesPorArticulo(String Idioma, String Articulo) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "NUMERAL.ID, " +
                "NIVEL.NIVEL_" + Idioma + ", " +
                "NUMERAL.NUMERAL_" + Idioma + " " +
                "FROM NUMERAL " +
                "INNER JOIN NIVEL ON NUMERAL.NIVEL_ID=NIVEL.ID " +
                "WHERE ARTICULO_ID=" + Articulo + " " +
                "ORDER BY NUMERAL.ID;", null);

        ArrayList<Modelo_NUMERAL> result = new ArrayList<Modelo_NUMERAL>();
        while (cursor.moveToNext()) {
            Modelo_NUMERAL articulo = new Modelo_NUMERAL(
                    cursor.getString(0),//ID
                    cursor.getString(1),//NIVEL
                    cursor.getString(2)//NUMERAL
            );
            result.add(articulo);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
