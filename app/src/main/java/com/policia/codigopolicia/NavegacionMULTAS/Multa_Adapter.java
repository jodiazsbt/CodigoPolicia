package com.policia.codigopolicia.NavegacionMULTAS;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_MULTA;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by 1085253556 on 19/01/2018.
 */

public class Multa_Adapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Modelo_MULTA> multas;

    public Multa_Adapter(Activity context, ArrayList<Modelo_MULTA> multas) {

        this.multas = multas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return multas.size();
    }

    @Override
    public Object getItem(int pos) {
        return multas.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return Long.parseLong(multas.get(pos).ID);
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.multa_adapter, null, true);

        TextView textViewLabel = view.findViewById(R.id.textViewLabel);
        TextView textViewValue = view.findViewById(R.id.textViewValue);

        String multa = multas.get(pos).Multa;
        Double valor = multas.get(pos).Valor;

        textViewLabel.setText(multas.get(pos).Nivel);

        if (valor == 0)
            textViewValue.setText(multa);
        else
            textViewValue.setText(multa + " " + NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(valor));

        return view;
    }
}
