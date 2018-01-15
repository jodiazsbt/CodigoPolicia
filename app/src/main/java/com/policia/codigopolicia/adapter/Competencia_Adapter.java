package com.policia.codigopolicia.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_COMPENTENCIA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 10/01/2018.
 */

public class Competencia_Adapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Modelo_COMPENTENCIA> compentencias;

    public Competencia_Adapter(Activity activity, ArrayList<Modelo_COMPENTENCIA> compentencias) {

        this.activity = activity;
        this.compentencias = compentencias;
    }

    @Override
    public int getCount() {
        return compentencias.size();
    }

    @Override
    public Object getItem(int position) {
        return compentencias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(compentencias.get(position).ID);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.articulo_dialog_competencia, null);

            Modelo_COMPENTENCIA competencia = compentencias.get(position);

            TextView textviewCOMPETENCIA = view.findViewById(R.id.textviewCOMPETENCIA);
            textviewCOMPETENCIA.setText(competencia.Competencia + " (INSTANCIA " + competencia.Instancia + ")");
        }

        return view;
    }
}
