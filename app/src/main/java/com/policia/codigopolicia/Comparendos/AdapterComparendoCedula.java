package com.policia.codigopolicia.Comparendos;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.remote.RemoteComportamiento;
import com.policia.remote.response.RNMCGENERAL2Response;
import com.policia.remote.response.RNMCGENERAL2Result;
import com.policia.remote.response.RNMCGENERALResponse;
import com.policia.remote.response.RNMCGENERALResult;
import com.policia.remote.response.RemoteComparendos;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 21/03/2018.
 */

public class AdapterComparendoCedula extends BaseExpandableListAdapter {

    private Fragment fragment;
    private ArrayList<DetalleCedula> expedientes;

    public AdapterComparendoCedula(Fragment fragment, RNMCGENERAL2Response expediente) {

        this.fragment = fragment;

        expedientes = new ArrayList<>();
        for (RNMCGENERAL2Result comportamiento : expediente.rNMCGENERAL2Result) {
            expedientes.add(new DetalleCedula(comportamiento));
        }
    }

    @Override
    public int getGroupCount() {

        return expedientes.size();
    }

    @Override
    public int getChildrenCount(int posicion) {

        return expedientes.get(posicion).count();
    }

    @Override
    public Object getGroup(int posicion) {

        return expedientes.get(posicion);
    }

    @Override
    public Object getChild(int grupoPosicion, int itemPosicion) {

        return expedientes.get(itemPosicion).getValor(itemPosicion);
    }

    @Override
    public long getGroupId(int posicion) {

        return posicion;
    }

    @Override
    public long getChildId(int grupoPosicion, int itemPosicion) {

        return expedientes.get(itemPosicion).getID();
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        if (view == null) {

            LayoutInflater inflater = fragment.getLayoutInflater();
            view = inflater.inflate(R.layout.comparendos_expediente_title, null, false);
        }
        TextView textviewExpediente = view.findViewById(R.id.textviewExpediente);
        textviewExpediente.setText(expedientes.get(groupPosition).getExpediente());

        ImageView imagebuttonMedidas = view.findViewById(R.id.imagebuttonMedidas);
        imagebuttonMedidas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DetalleCedula expediente = expedientes.get(groupPosition);
                new RemoteComparendos(
                        fragment.getActivity(),
                        expediente.getComportamiento())
                        .execute();
            }
        });

        ImageView imagebuttonComportamiento = view.findViewById(R.id.imagebuttonComportamiento);
        imagebuttonComportamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DetalleCedula expediente = expedientes.get(groupPosition);

                RemoteComportamiento.newInstance(
                        fragment.getActivity(),
                        expediente.getComportamiento(),
                        expediente.getExpediente()).execute();
            }
        });

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.comparendos_expediente_item, null);

        TextView textviewLabel = view.findViewById(R.id.textviewLabel);
        TextView textviewValue = view.findViewById(R.id.textviewValue);

        textviewLabel.setText(expedientes.get(groupPosition).getLabel(childPosition));
        textviewValue.setText(expedientes.get(groupPosition).getValor(childPosition));

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
