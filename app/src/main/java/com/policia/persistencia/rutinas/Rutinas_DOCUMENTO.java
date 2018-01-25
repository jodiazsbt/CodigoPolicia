package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_DOCUMENTO;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_DOCUMENTO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 15/01/2018.
 */

public class Rutinas_DOCUMENTO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_DOCUMENTO(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_DOCUMENTO> Documentos() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "DOCUMENTO_ESP, " +
                "URL " +
                "FROM DOCUMENTO;", null);

        ArrayList<Modelo_DOCUMENTO> result = new ArrayList<Modelo_DOCUMENTO>();
        while (cursor.moveToNext()) {
            Modelo_DOCUMENTO documento = new Modelo_DOCUMENTO(
                    cursor.getString(0),//DOCUMENTO
                    cursor.getString(1)//URL
            );
            result.add(documento);
        }
        cursor.close();
        DB.close();
        return result;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'DOCUMENTO';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_DOCUMENTO documento) {
        String[] parameters = new String[]{
                documento.DOCUMENTO_ESP + "",
                documento.URL + "",
                documento.ACTIVO + "",
                documento.TIPO_ARCHIVO_ID + "",
                documento.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'DOCUMENTO' SET DOCUMENTO_ESP=?,URL=?,ACTIVO=?,TIPO_ARCHIVO_ID=? WHERE ID=?", parameters);
        DB.close();
    }
}
