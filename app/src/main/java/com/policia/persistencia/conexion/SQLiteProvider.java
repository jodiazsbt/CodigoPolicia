package com.policia.persistencia.conexion;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.policia.codigopolicia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class SQLiteProvider extends SQLiteOpenHelper {

    private String path;
    private String name;
    private final Context context;

    public SQLiteProvider(Context context) {

        super(context, context.getString(R.string.dbname), null, Integer
                .parseInt(context.getString(R.string.dbversion)));

        this.context = context;
        this.path = context.getString(R.string.dbpath);
        this.name = context.getString(R.string.dbname);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        executeSQLScript(database, context.getString(R.string.SCRIPT));
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //SQLiteDatabase.deleteDatabase(new File(database.getPath()));
        database.close();
        context.deleteDatabase(database.getPath());
        executeSQLScript(database, context.getString(R.string.SCRIPT));
    }

    private void executeSQLScript(SQLiteDatabase database, String script) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(script);
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            String[] createScript = outputStream.toString().replace(");", ")=").replace("\r\n", "").split("=");
            for (int i = 0; i < createScript.length; i++) {
                String sqlStatement = createScript[i].trim();
                // TODO You may want to parse out comments here
                if (sqlStatement.length() > 0) {
                    database.execSQL(sqlStatement + ";");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
