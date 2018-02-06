package com.policia.codigopolicia.Comparendos;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.ValuePar;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.remote.response.RNMCDETALLECOMPORTAMIENTOResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1085253556 on 2/02/2018.
 */

public class AdapterComportamiento extends BaseAdapter {

    private Activity activity;
    private ArrayList<ValuePar> valores;

    public AdapterComportamiento(Activity activity, List<RNMCDETALLECOMPORTAMIENTOResult> comportamientos) {

        this.activity = activity;
        //this.comportamiento = comportamiento;
        this.valores = new ArrayList<>();

        try {
            for (RNMCDETALLECOMPORTAMIENTOResult comportamiento : comportamientos) {

                if (Seguridad.Sesion(activity).getIdiomaLargo().equals("ESP")) {

                    this.valores.add(new ValuePar("ARTICULO", comportamiento.aRTICULOCOMPORTAMIENTORNMCESP));
                    this.valores.add(new ValuePar("NUMERAL", comportamiento.nUMERALCOMPORTAMIENTORNMCESP));
                } else {

                    this.valores.add(new ValuePar("ARTICULO", comportamiento.aRTICULOCOMPORTAMIENTORNMCENG));
                    this.valores.add(new ValuePar("NUMERAL", comportamiento.nUMERALCOMPORTAMIENTORNMCENG));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return valores.size();
    }

    @Override
    public Object getItem(int i) {
        return valores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = activity.getLayoutInflater().inflate(R.layout.comparendos_comportamiento, null);

        ValuePar item = valores.get(i);

        TextView textviewLabel = view.findViewById(R.id.textviewLabel);
        TextView textviewValue = view.findViewById(R.id.textviewValue);

        textviewLabel.setText(item.getLabel());
        textviewValue.setText(item.getValue());

        return view;
    }
}
