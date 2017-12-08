package com.policia.codigopolicia;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.policia.codigopolicia.idioma.Idioma_Adapter;
import com.policia.negocio.logica.Negocio_IDIOMA;

public class IdiomaActivity extends Activity {

    private ListView listViewIdiomas;
    private Negocio_IDIOMA negocioIdioma;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.idioma_activity);

        negocioIdioma = new Negocio_IDIOMA(getBaseContext());

        listViewIdiomas = findViewById(R.id.listViewIdiomas);
        listViewIdiomas.addHeaderView(getLayoutInflater().inflate(R.layout.idioma_header, null), null, false);
        listViewIdiomas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
            }
        });
        listViewIdiomas.setAdapter(
                new Idioma_Adapter(
                        this,
                        negocioIdioma.Idiomas()));

    }
}
