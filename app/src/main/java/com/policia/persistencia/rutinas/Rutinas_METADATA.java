package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_Busqueda_Articulo;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_MEDIDA;
import com.policia.persistencia.tablas.Tabla_METADATA;

import java.util.ArrayList;

/**
 * Created by JORGE on 3/12/2017.
 */

public class Rutinas_METADATA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_METADATA(Context context) {

        this.context = context;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'METADATA';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_METADATA metadata) {
        String[] parameters = new String[]{
                metadata.METADATA_ESP,
                metadata.ACTIVO + "",
                metadata.ARTICULO_ID + "",
                metadata.FECHA + "",
                metadata.METADATA_ENG + "",
                metadata.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'METADATA' SET METADATA_ESP=?,ACTIVO=?,ARTICULO_ID=?,FECHA=?,METADATA_ENG=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM METADATA WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_METADATA metadata) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", metadata.ID);
        parameters.put("METADATA_ESP", metadata.METADATA_ESP);
        parameters.put("ACTIVO", metadata.ACTIVO);
        parameters.put("ARTICULO_ID", metadata.ARTICULO_ID);
        parameters.put("FECHA", metadata.FECHA);
        parameters.put("METADATA_ENG", metadata.METADATA_ENG);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'METADATA'", null, parameters);
        DB.close();
        return id > 0;
    }

    public ArrayList<Modelo_Busqueda_Articulo> BusquedaMETADATA(String Idioma, String termino_busqueda) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        //String[] parametros = new String[]{termino_busqueda};

        Cursor cursor = DB.rawQuery("" +
                "SELECT " +
                "CAPITULO.ID, " +
                "(SELECT COUNT(A.ID) FROM ARTICULO A WHERE A.CAPITULO_ID=CAPITULO.ID AND A.ID<ARTICULO.ID) POS," +
                "NIVEL.NIVEL_" + Idioma + " LIBRO, " +
                "NIVEL_TITULO.NIVEL_" + Idioma + " TITULO, " +
                "NIVEL_CAPITULO.NIVEL_" + Idioma + " CAPITULO, " +
                "NIVEL_ARTICULO.NIVEL_" + Idioma + " ARTICULO " +
                "FROM NIVEL " +
                "INNER JOIN LIBRO ON NIVEL.ID=LIBRO.NIVEL_ID " +
                "INNER JOIN TITULO ON LIBRO.ID=TITULO.LIBRO_ID " +
                "INNER JOIN NIVEL NIVEL_TITULO ON TITULO.NIVEL_ID=NIVEL_TITULO.ID " +
                "INNER JOIN CAPITULO ON TITULO.ID=CAPITULO.TITULO_ID " +
                "INNER JOIN NIVEL NIVEL_CAPITULO ON CAPITULO.NIVEL_ID=NIVEL_CAPITULO.ID " +
                "INNER JOIN ARTICULO ON CAPITULO.ID=ARTICULO.CAPITULO_ID " +
                "INNER JOIN NIVEL NIVEL_ARTICULO ON ARTICULO.NIVEL_ID=NIVEL_ARTICULO.ID " +
                "INNER JOIN METADATA ON METADATA.ARTICULO_ID=ARTICULO.ID " +
                "WHERE METADATA.METADATA_" + Idioma + " LIKE '%" + termino_busqueda + "%';", null);

        ArrayList<Modelo_Busqueda_Articulo> result = new ArrayList<Modelo_Busqueda_Articulo>();
        while (cursor.moveToNext()) {
            result.add(new Modelo_Busqueda_Articulo(
                    cursor.getString(0),    //ID CAPITULO
                    cursor.getInt(1),       //POSICION ARTICULO
                    cursor.getString(2),    //LIBRO
                    cursor.getString(3),    //TITULO
                    cursor.getString(4),    //CAPITULO
                    cursor.getString(5))    //ARTICULO
            );
        }
        cursor.close();
        DB.close();
        return result;
    }

}
