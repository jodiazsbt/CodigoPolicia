package com.policia.codigopolicia.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_MEDIDA;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by 1085253556 on 11/01/2018.
 */

public class Comparendo_Adapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Modelo_MEDIDA> medidas;

    public Comparendo_Adapter(Activity activity, ArrayList<Modelo_MEDIDA> medidas) {

        this.activity = activity;
        this.medidas = medidas;
    }

    @Override
    public int getCount() {
        return medidas.size();
    }

    @Override
    public Object getItem(int position) {
        return medidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.articulo_dialog_comparendo, null);

        TextView textviewCOMPORTAMIENTO = view.findViewById(R.id.textviewCOMPORTAMIENTO);
        TextView textviewMEDIDA = view.findViewById(R.id.textviewMEDIDA);
        TextView textviewMULTA = view.findViewById(R.id.textviewMULTA);

        Modelo_MEDIDA medida = medidas.get(position);

        textviewCOMPORTAMIENTO.setText(medida.Nivel + " " + medida.Comportamiento);
        textviewMEDIDA.setText(medida.Medida);
        textviewMULTA.setText(NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(medida.Valor));

        return view;
    }
}
