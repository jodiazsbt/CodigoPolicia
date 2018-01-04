package com.policia.codigopolicia.NavegacionCNPC;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.policia.codigopolicia.R;
import com.policia.negocio.logica.Negocio_LIBRO;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Fragment_LIBRO extends Fragment {

    private ExpandableListView expandableLibro;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.libro_fragment, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle state) {
        super.onActivityCreated(state);

        try {
            expandableLibro = getView().findViewById(R.id.expandableLibro);
            expandableLibro.addHeaderView(getLayoutInflater().inflate(R.layout.libro_expandable_header, null), null, false);
            expandableLibro.setAdapter(new Nivel_BaseExpandableListAdapter(this, new Negocio_LIBRO(getContext()).Libros()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
