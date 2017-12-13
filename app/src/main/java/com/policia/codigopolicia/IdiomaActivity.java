package com.policia.codigopolicia;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.policia.codigopolicia.idioma.Idioma_Adapter;
import com.policia.negocio.logica.Negocio_IDIOMA;
import com.policia.negocio.modelo.Modelo_IDIOMA;
import com.policia.negocio.seguridad.Seguridad;

import java.util.ArrayList;
import java.util.Locale;

public class IdiomaActivity extends Activity {

    private Idioma_Adapter adapter;
    private ListView listViewIdiomas;
    private Negocio_IDIOMA negocioIdioma;

    private int posicion = 0;
    ArrayList<Modelo_IDIOMA> idiomas;

    private final Activity activity;

    public IdiomaActivity() {
        activity = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.idioma_activity);

        negocioIdioma = new Negocio_IDIOMA(getBaseContext());

        listViewIdiomas = findViewById(R.id.listViewIdiomas);
        listViewIdiomas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewIdiomas.addHeaderView(getLayoutInflater().inflate(R.layout.idioma_header, null), null, false);
        listViewIdiomas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                posicion = i;
                view.setSelected(true);
            }
        });
        idiomas = negocioIdioma.Idiomas();

        adapter = new Idioma_Adapter(activity, idiomas);
        listViewIdiomas.setAdapter(adapter);
        listViewIdiomas.setItemChecked(posicionIdioma(), true);
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setLocale(String idioma) {

        Locale locale = new Locale(idioma);
        Locale.setDefault(locale);
        Configuration configuration = activity.getResources().getConfiguration();
        configuration.setLocale(locale);
        activity.createConfigurationContext(configuration);
    }

    private int posicionIdioma() {

        for (Modelo_IDIOMA idioma : idiomas) {
            posicion++;
            if (idioma.SELECCION) {
                break;
            }
        }
        return posicion;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void clickAceptar(View view) {
        try {

            Modelo_IDIOMA idioma = idiomas.get(posicion - 1);
            Locale locale = new Locale(idioma.CODIGO);
            Resources resources = getResources();
            Configuration configuration = resources.getConfiguration();
            configuration.setLocale(locale);
            getApplicationContext().createConfigurationContext(configuration);

            Seguridad sesion = Seguridad.Sesion(activity.getBaseContext());
            sesion.actualizarIdiomaSesion(idioma.CODIGO);

            Toast.makeText(activity, getString(R.string.toast_locale_success) + " " + sesion.getIdiomaNombre(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, R.string.toast_locale_exception, Toast.LENGTH_SHORT).show();
        } finally {

            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    public void clickCancelar(View view) {

        this.finish();
    }
}