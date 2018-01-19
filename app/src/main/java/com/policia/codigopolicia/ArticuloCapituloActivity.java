package com.policia.codigopolicia;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.policia.codigopolicia.NavegacionCNPC.CNPC_FragmentStatePagerAdapter;
import com.policia.codigopolicia.NavegacionCNPC.WrapContentViewPager;
import com.policia.negocio.logica.Negocio_ARTICULO;
import com.policia.negocio.modelo.Modelo_ARTICULO;

import java.util.ArrayList;
import java.util.Locale;

public class ArticuloCapituloActivity extends FragmentActivity implements TextToSpeech.OnInitListener, View.OnClickListener {

    WrapContentViewPager viewPagerArticulos;
    PagerAdapter pagerAdapterArticulo;
    FloatingActionButton fab;

    private int counter = 0;
    private ShowcaseView showcaseView;

    private int posicion;
    private String termino;
    private String capitulo;

    private int MY_DATA_CHECK_CODE = 0;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articulo_activity);

        posicion = getIntent().getExtras().getInt("posicion");
        termino = getIntent().getExtras().get("termino") == null ? "" : getIntent().getExtras().get("termino").toString();
        capitulo = getIntent().getExtras().get("capitulo") == null ? "" : getIntent().getExtras().get("capitulo").toString();

        try {
            int paginas = 0;
            paginas = new Negocio_ARTICULO(this).CantidadArticulosPorCapitulo(capitulo);

            Intent checkTTSIntent = new Intent();
            checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

            fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Negocio_ARTICULO negocioArticulo = null;
                    try {
                        int position = viewPagerArticulos.getCurrentItem();
                        negocioArticulo = new Negocio_ARTICULO(view.getContext());

                        ArrayList<Modelo_ARTICULO> articulos = null;
                        articulos = negocioArticulo.ArticulosPorCapitulo(capitulo, position + 1);
                        for (Modelo_ARTICULO articulo : articulos) {
                            textToSpeech.speak(articulo.Articulo_Descripcion, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            viewPagerArticulos = findViewById(R.id.viewPagerArticulos);
            pagerAdapterArticulo = new CNPC_FragmentStatePagerAdapter(getSupportFragmentManager(), capitulo, termino, paginas);
            viewPagerArticulos.setAdapter(pagerAdapterArticulo);
            viewPagerArticulos.setCurrentItem(posicion);

            showcaseView = new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .singleShot(R.layout.articulo_activity)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setContentTitle("Artículos")
                    .setContentText("Deslice con el dedo la pantalla hacia el lado derecho o izquierdo para pasar entre artículos.")
                    .setOnClickListener(this)
                    .build();
            showcaseView.setButtonText(getResources().getString(R.string.showcaseSiguiente));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
        if (viewPagerArticulos.getCurrentItem() == 0) {
            //salir si no tiene mas paginas para visualizar
            super.onBackPressed();
        } else {
            //devolverse si tiene mas paginas para visualizar
            viewPagerArticulos.setCurrentItem(viewPagerArticulos.getCurrentItem() - 1);
        }
        */
    }

    @Override
    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            fab.setClickable(true);
            textToSpeech.setLanguage(new Locale(getString(R.string.language), getString(R.string.country)));
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                textToSpeech = new TextToSpeech(this, this);
            } else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (counter) {
            case 0:

                ViewTarget viewTarget = new ViewTarget(fab);
                showcaseView.setTarget(viewTarget);
                showcaseView.setContentTitle("Sintetizador");
                showcaseView.setContentText("Utilice el sintetizador para ayudar a las personas con discapacidad visual a escuchar el contenido de los artículos.");
                showcaseView.setButtonText(getResources().getString(R.string.showcaseEntendido));
                break;
            case 1:
                showcaseView.hide();
                break;
        }
        counter++;
    }
}
