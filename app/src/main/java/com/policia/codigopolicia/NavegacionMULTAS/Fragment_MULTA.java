package com.policia.codigopolicia.NavegacionMULTAS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.policia.codigopolicia.R;
import com.policia.negocio.logica.Negocio_MULTA;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Fragment_MULTA extends Fragment {

    private ExpandableListView expandableMulta;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.multa_fragment, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle state) {
        super.onActivityCreated(state);

        try {
            expandableMulta = getView().findViewById(R.id.expandableMulta);
            expandableMulta.addHeaderView(getLayoutInflater().inflate(R.layout.multa_expandable_header, null));
            expandableMulta.setAdapter(new Nivel_BaseExpandableListAdapter(this, new Negocio_MULTA(getContext()).Libros()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
