package com.policia.persistencia.rutinas;

import android.content.ContentValues;
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

    public int countDocumentos() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM DOCUMENTO WHERE DOCUMENTO.ACTIVO=1;", null);

        int count = 0;
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return count;
    }

    public ArrayList<Modelo_DOCUMENTO> Documentos() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT " +
                "DOCUMENTO.DOCUMENTO_ESP, " +
                "DOCUMENTO.URL, " +
                "TIPO_ARCHIVO.RECURSO_ID " +
                "FROM DOCUMENTO " +
                "INNER JOIN TIPO_ARCHIVO ON DOCUMENTO.TIPO_ARCHIVO_ID=TIPO_ARCHIVO.ID " +
                "WHERE DOCUMENTO.ACTIVO=1;", null);

        ArrayList<Modelo_DOCUMENTO> result = new ArrayList<Modelo_DOCUMENTO>();
        while (cursor.moveToNext()) {
            Modelo_DOCUMENTO documento = new Modelo_DOCUMENTO(
                    cursor.getString(0),//DOCUMENTO
                    cursor.getString(1),//URL
                    cursor.getString(2)//RECURSO_ID
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
                documento.ACTIVO ? "1" : "0" + "",
                documento.TIPO_ARCHIVO_ID + "",
                documento.UBICACION + "",
                documento.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'DOCUMENTO' SET DOCUMENTO_ESP=?,URL=?,ACTIVO=?,TIPO_ARCHIVO_ID=?,UBICACION=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM DOCUMENTO WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_DOCUMENTO documento) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", documento.ID);
        parameters.put("DOCUMENTO_ESP", documento.DOCUMENTO_ESP);
        parameters.put("DOCUMENTO_ENG", documento.DOCUMENTO_ENG);
        parameters.put("URL", documento.URL);
        parameters.put("ACTIVO", documento.ACTIVO ? "1" : "0");
        parameters.put("TIPO_ARCHIVO_ID", documento.TIPO_ARCHIVO_ID);
        parameters.put("UBICACION", documento.UBICACION);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'DOCUMENTO'", null, parameters);
        DB.close();
        return id > 0;
    }
}
