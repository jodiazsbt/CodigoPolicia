package com.policia.codigopolicia.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.policia.codigopolicia.R;

/**
 * Created by 1085253556 on 15/01/2018.
 */

public class Fragment_CAPACITACION extends Fragment {

    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View view = inflater.inflate(
                R.layout.capacitacion_lista, container, false);

        lv = view.findViewById(R.id.listviewCapacitacion);

        return view;
    }
}
