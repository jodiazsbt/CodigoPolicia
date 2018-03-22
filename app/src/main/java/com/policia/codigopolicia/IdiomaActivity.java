package com.policia.codigopolicia;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.policia.codigopolicia.idioma.Idioma_Adapter;
import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.negocio.logica.Negocio_IDIOMA;
import com.policia.negocio.modelo.Modelo_IDIOMA;
import com.policia.negocio.seguridad.Seguridad;

import java.util.ArrayList;

/**
* IdiomaActivity:
*
* Esta actividad permite hacer el cambio de idioma de la aplicacion completa
* */
public class IdiomaActivity extends Activity {

    private Idioma_Adapter adapter;
    private ListView listViewIdiomas;
    private Negocio_IDIOMA negocioIdioma;

    ArrayList<Modelo_IDIOMA> idiomas;

    private final Activity activity;

    public IdiomaActivity() {
        activity = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idioma_activity);
        negocioIdioma = new Negocio_IDIOMA(getBaseContext());

        listViewIdiomas = findViewById(R.id.listViewIdiomas);
        listViewIdiomas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewIdiomas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) {
                view.setSelected(true);
                clickAceptar(posicion);
            }
        });
        idiomas = negocioIdioma.Idiomas();

        View header;
        try {
            header = getLayoutInflater().inflate(R.layout.idioma_header, null);

            new Negocio_DOCUMENTO(getBaseContext()).drawAVATAR(Negocio_DOCUMENTO.AVATAR.SCREEN_IDIOMA,
                    (ImageView) header.findViewById(R.id.imageViewCaricatura));

            listViewIdiomas.addHeaderView(header, null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new Idioma_Adapter(activity, idiomas);
        listViewIdiomas.setAdapter(adapter);
        listViewIdiomas.setItemChecked(posicionIdioma(), true);
    }

    /**
    * Este metodo obtiene el idioma seleccionado
    * */
    private int posicionIdioma() {
        int posicion = 0;
        for (Modelo_IDIOMA idioma : idiomas) {
            posicion++;
            if (idioma.SELECCION) {
                break;
            }
        }
        return posicion;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void clickAceptar(int posicion) {//View view) {

        try {
            Seguridad sesion = Seguridad.Sesion(activity.getBaseContext());

            String idioma = idiomas.get(posicion - 1).CODIGO;
            sesion.actualizarIdiomaSesion(idioma);

            Toast.makeText(activity, getString(R.string.toast_locale_success) + " " + sesion.getIdiomaNombre(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            Intent refresh = new Intent(this, PrincipalActivity.class);
            startActivity(refresh);

            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);

            finish();
        }

    }

    public void clickCancelar(View view) {

        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}