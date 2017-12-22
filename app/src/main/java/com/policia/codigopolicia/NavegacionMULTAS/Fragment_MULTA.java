package com.policia.codigopolicia.NavegacionMULTAS;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.policia.codigopolicia.ArticuloCapituloActivity;
import com.policia.codigopolicia.ArticuloMultaActivity;
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
            expandableMulta.setAdapter(new Multa_BaseExpandableListAdapter(this, new Negocio_MULTA(getContext()).Multas()));
            expandableMulta.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {

                    Intent intent = new Intent(view.getContext(), ArticuloMultaActivity.class);
                    intent.putExtra("multa", ((Multa_BaseExpandableListAdapter) parent.getExpandableListAdapter()).MultaID(groupPosition));
                    intent.putExtra("categoria", id);
                    intent.putExtra("posicion", 0);//primera página
                    getContext().startActivity(intent);

                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
