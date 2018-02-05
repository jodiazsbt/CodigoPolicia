package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_CATEGORIA;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_CATEGORIA;
import com.policia.persistencia.tablas.Tabla_COMPETENCIA;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Rutinas_CATEGORIA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_CATEGORIA(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_CATEGORIA> CategoriaPorTipoMulta(String Idioma, String TipoMulta) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                TipoMulta + ""};

        Cursor cursor = DB.rawQuery("SELECT DISTINCT " +
                "B.ID, B.CATEGORIA_ESP,B.RECURSO_ID " +
                "FROM MULTA A " +
                "INNER JOIN " +
                "CATEGORIA B ON A.CATEGORIA_ID = B.ID " +
                "WHERE A.TIPOMULTA_ID=? " +
                "ORDER BY B.ID ASC;", parameters);

        ArrayList<Modelo_CATEGORIA> result = new ArrayList<Modelo_CATEGORIA>();
        while (cursor.moveToNext()) {
            Modelo_CATEGORIA categoria = new Modelo_CATEGORIA(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            result.add(categoria);
        }
        cursor.close();
        DB.close();
        return result;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'CATEGORIA';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_CATEGORIA categoria) {
        String[] parameters = new String[]{
                categoria.CATEGORIA_ESP,
                categoria.CATEGORIA_ENG + "",
                categoria.VIGENTE + "",
                categoria.FECHA + "",
                categoria.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'CATEGORIA' SET CATEGORIA_ESP=?,CATEGORIA_ENG=?,VIGENTE=?,FECHA=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM CATEGORIA WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_CATEGORIA categoria) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", categoria.ID);
        parameters.put("CATEGORIA_ESP", categoria.CATEGORIA_ESP);
        parameters.put("CATEGORIA_ENG", categoria.CATEGORIA_ENG);
        parameters.put("VIGENTE", categoria.VIGENTE);
        parameters.put("FECHA", categoria.FECHA);
        parameters.put("RECURSO_ID", categoria.RECURSO_ID);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'CATEGORIA'", null, parameters);
        DB.close();
        return id > 0;
    }
}
