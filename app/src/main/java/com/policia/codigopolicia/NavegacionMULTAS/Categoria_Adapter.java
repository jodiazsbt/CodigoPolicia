package com.policia.codigopolicia.NavegacionMULTAS;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_CATEGORIA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 19/01/2018.
 */

public class Categoria_Adapter extends BaseAdapter {

    public Activity context;
    private ArrayList<Modelo_CATEGORIA> categorias;

    public Categoria_Adapter(Activity context, ArrayList<Modelo_CATEGORIA> categorias) {

        this.context = context;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int pos) {
        return categorias.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return Long.parseLong(categorias.get(pos).ID);
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.multa_titulo, null);

        TextView textViewLabel = view.findViewById(R.id.textViewLabel);
        //TextView textViewValue = view.findViewById(R.id.textViewValue);

        textViewLabel.setText(categorias.get(pos).Categoria);
        //textViewValue.setText(categorias.get(groupPosition).getCategorias().get(childPosition).Categoria);

        return view;
    }
}
