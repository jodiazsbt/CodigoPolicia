package com.policia.codigopolicia.Comparendos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.policia.codigopolicia.R;
import com.policia.remote.response.RNMCGENERALResponse;

/**
 * Created by 1085253556 on 1/02/2018.
 */

public class FragmentComparendoExpediente extends Fragment {

    private ExpandableListView expandableExpendiente;

    private RNMCGENERALResponse expediente;

    public static FragmentComparendoExpediente newInstance(Activity activity, RNMCGENERALResponse expediente) {

        Bundle args = new Bundle();

        FragmentComparendoExpediente fragment = new FragmentComparendoExpediente();
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
        expandableExpendiente.setAdapter(new AdapterExpendienteTitle(this, expediente));

        return fragment;
    }
}
