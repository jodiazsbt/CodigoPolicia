package com.policia.codigopolicia.Puntos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 27/12/2017.
 */

public class PuntosAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<PuntosCercanos> puntosCercanos;

    public PuntosAdapter(Activity activity, ArrayList<PuntosCercanos> puntos) {

        this.activity = activity;
        this.puntosCercanos = puntos;
    }

    @Override
    public int getCount() {
        return puntosCercanos.size();
    }

    @Override
    public Object getItem(int i) {
        return puntosCercanos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {

            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.puntos_list_item, null);

            TextView textViewNombre = view.findViewById(R.id.textViewNombre);
            TextView textViewDireccion = view.findViewById(R.id.textViewDireccion);
            TextView textViewDistancia = view.findViewById(R.id.textViewDistancia);

            textViewNombre.setText(puntosCercanos.get(i).Nombre);
            textViewDireccion.setText(puntosCercanos.get(i).Direccion);
            textViewDistancia.setText(String.valueOf(puntosCercanos.get(i).Distancia) + " m");

        }


        return view;
    }
}
