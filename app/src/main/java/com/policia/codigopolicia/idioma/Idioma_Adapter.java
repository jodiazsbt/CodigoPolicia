package com.policia.codigopolicia.idioma;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_IDIOMA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 5/12/2017.
 */

public class Idioma_Adapter extends BaseAdapter {

    Activity context;
    ArrayList<Modelo_IDIOMA> idiomas;

    public Idioma_Adapter(Activity context, ArrayList<Modelo_IDIOMA> idiomas) {

        this.context = context;
        this.idiomas = idiomas;
    }

    @Override
    public int getCount() {

        return idiomas.size();
    }

    @Override
    public Object getItem(int posicion) {

        return idiomas.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {

        return posicion;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup parent) {
        view = context.getLayoutInflater().inflate(R.layout.idioma_listitem, null);

        ((ListView)parent).setItemChecked(posicion, true);

        TextView textViewIdioma = view.findViewById(R.id.textViewIdioma);
        textViewIdioma.setText(idiomas.get(posicion).IDIOMA);

        return view;
    }
}
