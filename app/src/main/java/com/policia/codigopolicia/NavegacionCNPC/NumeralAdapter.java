package com.policia.codigopolicia.NavegacionCNPC;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_NUMERAL;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 27/12/2017.
 */

public class NumeralAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Modelo_NUMERAL> numerales;
    private DocumentView documentViewNUMERAL;

    public NumeralAdapter(Activity activity, ArrayList<Modelo_NUMERAL> numerales) {

        this.activity = activity;
        this.numerales = numerales;
    }

    @Override
    public int getCount() {
        return numerales.size();
    }

    @Override
    public Object getItem(int position) {
        return numerales.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {

            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.articulo_numeral_item, null);

            Modelo_NUMERAL numeral = numerales.get(position);

            String texto_numeral = numeral.Nivel + "\t" + numeral.Numeral;

            //documentViewNUMERAL = view.findViewById(R.id.documentViewNUMERAL);
            //documentViewNUMERAL.setText(texto_numeral);

            TextView textViewNUMERAL = view.findViewById(R.id.textViewNUMERAL);
            textViewNUMERAL.setText(texto_numeral);
        }

        return view;
    }
}
