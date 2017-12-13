package com.policia.codigopolicia.idioma;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_IDIOMA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 5/12/2017.
 */

public class Idioma_Adapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Modelo_IDIOMA> idiomas;

    public Idioma_Adapter(Activity context, ArrayList<Modelo_IDIOMA> idiomas) {

        this.context = context;
        this.idiomas = idiomas;
    }

    @Override
    public int getCount() {
        return idiomas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = context.getLayoutInflater().from(context).inflate(R.layout.idioma_listitem, viewGroup, false);
        }

        TextView textViewIdioma = view.findViewById(R.id.textViewIdioma);
        textViewIdioma.setText(idiomas.get(i).IDIOMA);

        return view;
    }
}
