package com.policia.codigopolicia.Comparendos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policia.codigopolicia.R;

/**
 * Created by 1085253556 on 8/02/2018.
 */

public class FragmentoComparendoEsperando extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.comparendos_esperando, container, false);

        return fragment;
    }
}
