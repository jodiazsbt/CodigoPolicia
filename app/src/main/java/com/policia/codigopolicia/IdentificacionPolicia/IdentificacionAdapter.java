package com.policia.codigopolicia.IdentificacionPolicia;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.ValuePar;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class IdentificacionAdapter extends BaseAdapter {

    private Fragment fragment;
    private ArrayList<ValuePar> valores;

    public IdentificacionAdapter(Fragment fragment, ArrayList<ValuePar> valores) {

        this.valores = valores;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return valores.size();
    }

    @Override
    public Object getItem(int pos) {
        return valores.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = fragment.getLayoutInflater();
        view = inflater.inflate(R.layout.policia_identificacion_item, null);

        TextView textViewLabel = view.findViewById(R.id.textViewLabel);
        TextView textViewValue = view.findViewById(R.id.textViewValue);

        textViewLabel.setText(valores.get(pos).getLabel());
        textViewValue.setText(valores.get(pos).getValue());
        return view;
    }
}
