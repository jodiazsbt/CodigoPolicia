package com.policia.codigopolicia.Comparendos;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.remote.response.RNMCMEDIDACORRECTIVAResult;

import java.util.List;

/**
 * Created by 1085253556 on 2/02/2018.
 */

public class AdapterComparendos extends BaseAdapter {

    private Activity activity;
    private List<RNMCMEDIDACORRECTIVAResult> comparendos;

    public AdapterComparendos(Activity activity, List<RNMCMEDIDACORRECTIVAResult> comparendos) {

        this.activity = activity;
        this.comparendos = comparendos;
    }

    @Override
    public int getCount() {
        return comparendos.size();
    }

    @Override
    public Object getItem(int i) {
        return comparendos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = activity.getLayoutInflater().inflate(R.layout.comparendos_comparendos, null);

        RNMCMEDIDACORRECTIVAResult item = comparendos.get(i);

        TextView textviewMedidaValue = view.findViewById(R.id.textviewMedidaValue);
        TextView textviewAtribucionValue = view.findViewById(R.id.textviewAtribucionValue);
        TextView textviewValorValue = view.findViewById(R.id.textviewValorValue);

        textviewMedidaValue.setText(item.rNMCMEDIDAESP);
        textviewAtribucionValue.setText(item.rNMCATRIBUCIONESP);
        textviewValorValue.setText(item.rNMCVALORESP);
        return view;
    }
}
