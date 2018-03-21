package com.policia.codigopolicia.Comparendos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.policia.codigopolicia.R;
import com.policia.remote.response.RNMCGENERAL2Response;

/**
 * Created by 1085253556 on 21/03/2018.
 */

public class FragmentoComparendoCedula extends Fragment {

    private ExpandableListView expandableExpendiente;

    private RNMCGENERAL2Response expediente;

    public static FragmentoComparendoCedula newInstance(Activity activity, RNMCGENERAL2Response expediente) {

        Bundle args = new Bundle();

        FragmentoComparendoCedula fragment = new FragmentoComparendoCedula();
        fragment.setArguments(args);
        fragment.expediente = expediente;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.comparendos_expediente, container, false);

        expandableExpendiente = fragment.findViewById(R.id.expandableExpendiente);
        expandableExpendiente.setAdapter(new AdapterComparendoCedula(this, expediente));

        return fragment;
    }
}
