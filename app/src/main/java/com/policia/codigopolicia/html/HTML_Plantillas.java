package com.policia.codigopolicia.html;

import android.content.Context;
import android.content.res.AssetManager;

import com.policia.codigopolicia.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 1085253556 on 5/01/2018.
 */

public class HTML_Plantillas {

    private String plantilla;
    private Context context;

    public HTML_Plantillas(Context context, Plantilla plantilla) {

        this.context = context;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(nombrePlantilla(plantilla));
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            this.plantilla = outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPlantilla() {
        return this.plantilla;
    }

    private String nombrePlantilla(Plantilla plantilla) {
        switch (plantilla) {
            case ARTICULO:
                return context.getResources().getString(R.string.html_plantilla_articulos);
            case TERMINOS:
                return context.getResources().getString(R.string.html_plantilla_terminos);
            case PROCEDIMIENTOS:
                return context.getResources().getString(R.string.html_plantilla_procedimientos);
            default:
                return "";
        }
    }

    public enum Plantilla {

        ARTICULO,
        TERMINOS,
        PROCEDIMIENTOS;
    }
}
