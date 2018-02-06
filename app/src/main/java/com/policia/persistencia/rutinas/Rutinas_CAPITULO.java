package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_CAPITULO;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_CAPITULO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Rutinas_CAPITULO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_CAPITULO(Context context) {

        this.context = context;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'CAPITULO';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_CAPITULO capitulo) {
        String[] parameters = new String[]{
                capitulo.CAPITULO_ESP,
                capitulo.VIGENTE + "",
                capitulo.NIVEL_ID + "",
                capitulo.TITULO_ID + "",
                capitulo.FECHA + "",
                capitulo.CAPITULO_ENG + "",
                capitulo.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'CAPITULO' SET CAPITULO_ESP=?,VIGENTE=?,NIVEL_ID=?,TITULO_ID=?,FECHA=?,CAPITULO_ENG=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM CAPITULO WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_CAPITULO capitulo) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", capitulo.ID);
        parameters.put("CAPITULO_ESP", capitulo.CAPITULO_ESP);
        parameters.put("VIGENTE", capitulo.VIGENTE);
        parameters.put("NIVEL_ID", capitulo.NIVEL_ID);
        parameters.put("TITULO_ID", capitulo.TITULO_ID);
        parameters.put("FECHA", capitulo.FECHA);
        parameters.put("CAPITULO_ENG", capitulo.CAPITULO_ENG);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'CAPITULO'", null, parameters);
        DB.close();
        return id > 0;
    }

    public ArrayList<Modelo_CAPITULO> CapitulosPorTitulo(String Idioma, String Titulo) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Titulo + ""};

        Cursor cursor = DB.rawQuery("SELECT " +
                "CAPITULO.ID, " +
                "NIVEL.NIVEL_" + Idioma + " NIVEL, " +
                "CAPITULO.CAPITULO_" + Idioma + " CAPITULO " +
                "FROM CAPITULO " +
                "INNER JOIN NIVEL ON CAPITULO.NIVEL_ID=NIVEL.ID " +
                "WHERE CAPITULO.TITULO_ID=? " +
                "ORDER BY NIVEL.ID;", parameters);

        ArrayList<Modelo_CAPITULO> result = new ArrayList<Modelo_CAPITULO>();
        while (cursor.moveToNext()) {
            Modelo_CAPITULO capitulo = new Modelo_CAPITULO(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            result.add(capitulo);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
