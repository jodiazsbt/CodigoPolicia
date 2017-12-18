package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Capitulos.CapitulosResultEntry;
import com.policia.negocio.modelo.Modelo_CAPITULO;
import com.policia.persistencia.conexion.SQLiteProvider;

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

    public String getUltimaActualizacion() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT MAX(FECHA) FROM 'CAPITULO';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }
        maxFecha = "01.12.2017";

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public boolean updateRecord(CapitulosResultEntry cre) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("CAPITULO_ESP", cre.NombreCapitulo);
            contentValues.put("CAPITULO_ENG", cre.NombreCapitulo);
            contentValues.put("VIGENTE",cre.Vigente_Capitulo);
            contentValues.put("NIVEL_ID",cre.ID_Nivel_Capitulo);
            contentValues.put("FECHA",cre.Fecha_Capitulo);
            DB.update("CAPITULO", contentValues, "ID" + " = " + cre.Id_Capitulo, null);
            DB.close();
        } catch (Exception e) {
            return false;
        }
        return true;
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
                "WHERE CAPITULO.TITULO_ID=?;", parameters);

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
