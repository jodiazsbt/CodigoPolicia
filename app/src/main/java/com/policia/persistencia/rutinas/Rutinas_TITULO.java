package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Titulos.TitulosResultEntry;
import com.policia.negocio.modelo.Modelo_TITULO;
import com.policia.persistencia.conexion.SQLiteProvider;

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

    public String getUltimaActualizacion() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT MAX(FECHA) FROM 'TITULO';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }
        maxFecha = "01.12.2017";

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public boolean updateRecord(TitulosResultEntry tre) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TITULO_ESP", tre.NombreTitulo);
            contentValues.put("TITULO_ENG", tre.NombreTitulo);
            contentValues.put("VIGENTE",tre.Vigente_Titulo);
            contentValues.put("NIVEL_ID",tre.ID_Nivel_Titulo);
            contentValues.put("FECHA",tre.Fecha_Titulo);
            DB.update("TITULO", contentValues, "ID" + " = " + tre.ID_Libro_Titulo, null);
            DB.close();
        } catch (Exception e) {
            return false;
        }
        return true;
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
