package com.policia.codigopolicia.idioma;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by 1085253556 on 11/12/2017.
 */

public class Idioma_Configuracion {

    public static boolean updateResources(Context context, String language) {
        Locale locale = new Locale(language.substring(0, 2));
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return true;
    }
}
