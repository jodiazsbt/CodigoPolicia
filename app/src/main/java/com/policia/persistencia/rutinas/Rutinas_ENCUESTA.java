package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_ENCUESTA;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_ENCUESTA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class Rutinas_ENCUESTA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_ENCUESTA(Context context) {

        this.context = context;
    }

    public int create(Tabla_ENCUESTA encuesta) {
        int id;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", encuesta.ID);
        parameters.put("ENCUESTA_ESP", encuesta.ENCUESTA_ESP);
        parameters.put("ENCUESTA_ENG", encuesta.ENCUESTA_ENG);
        parameters.put("ACTIVA", encuesta.ACTIVA);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = (int) DB.insert("'ENCUESTA'", null, parameters);
        DB.close();
        return id;
    }

    public void inactivar(Tabla_ENCUESTA encuesta) {
        String[] parameters = new String[]{
                encuesta.ACTIVA + "",
                encuesta.ID,};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'ENCUESTA' SET ACTIVA=? WHERE ID=?", parameters);
        DB.close();
    }

    public ArrayList<Long> existeEncuesta(String[] IDs) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        int cx = IDs.length;
        String UNION = "";
        if (cx > 0) {
            int i = 0;
            UNION += "(";
            do {
                UNION += "SELECT " + IDs[i++] + " ID " + (cx == i ? "" : "UNION ");
            }
            while (i < cx);

            UNION += ") NUEVO";
        }

        Cursor cursor = DB.rawQuery("SELECT " +
                "NUEVO.ID " +
                "FROM " + UNION + " " +
                "LEFT JOIN ENCUESTA ON NUEVO.ID=ENCUESTA.ID WHERE COALESCE(ENCUESTA.ACTIVA,0)=0;", null);//SOLO LIBROS

        ArrayList<Long> result = new ArrayList<Long>();
        while (cursor.moveToNext()) {
            result.add(cursor.getLong(0));
        }
        cursor.close();
        DB.close();
        return result;
    }

    public Modelo_ENCUESTA ultimaEncuesta(String idioma) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT ID,ENCUESTA_" + idioma + " FROM ENCUESTA WHERE ID=(SELECT MAX(ID) FROM ENCUESTA WHERE ACTIVA=1);", null);//SOLO LIBROS

        Modelo_ENCUESTA result = null;
        while (cursor.moveToNext()) {
            result = new Modelo_ENCUESTA(
                    cursor.getString(0),//ID
                    cursor.getString(1)//PREGUNTA
            );
        }
        cursor.close();
        DB.close();
        return result;
    }

}
