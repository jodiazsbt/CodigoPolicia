package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_TITULO;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_TITULO;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Rutinas_TITULO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_TITULO(Context context) {
        this.context = context;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'TITULO';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_TITULO titulo) {
        String[] parameters = new String[]{
                titulo.TITULO_ESP,
                titulo.VIGENTE + "",
                titulo.NIVEL_ID + "",
                titulo.LIBRO_ID + "",
                titulo.FECHA + "",
                titulo.TITULO_ENG + "",
                titulo.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'TITULO' SET TITULO_ESP=?,VIGENTE=?,NIVEL_ID=?,LIBRO_ID=?,FECHA=?,TITULO_ENG=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM TITULO WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_TITULO titulo) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", titulo.ID);
        parameters.put("TITULO_ESP", titulo.TITULO_ESP);
        parameters.put("VIGENTE", titulo.VIGENTE);
        parameters.put("NIVEL_ID", titulo.NIVEL_ID);
        parameters.put("LIBRO_ID", titulo.LIBRO_ID);
        parameters.put("FECHA", titulo.FECHA);
        parameters.put("TITULO_ENG", titulo.TITULO_ENG);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'TITULO'", null, parameters);
        DB.close();
        return id > 0;
    }

    public ArrayList<Modelo_TITULO> TitulosPorLibro(String Idioma, String Libro) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Libro + ""};

        Cursor cursor = DB.rawQuery("SELECT " +
                "TITULO.ID, " +
                "NIVEL.NIVEL_" + Idioma + " NIVEL, " +
                "TITULO.TITULO_" + Idioma + " TITULO " +
                "FROM TITULO " +
                "INNER JOIN NIVEL ON TITULO.NIVEL_ID=NIVEL.ID " +
                "WHERE TITULO.LIBRO_ID=?;", parameters);

        ArrayList<Modelo_TITULO> result = new ArrayList<Modelo_TITULO>();
        while (cursor.moveToNext()) {
            Modelo_TITULO titulo = new Modelo_TITULO(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            result.add(titulo);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
