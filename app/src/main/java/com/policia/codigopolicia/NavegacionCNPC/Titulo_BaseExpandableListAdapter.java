package com.policia.codigopolicia.NavegacionCNPC;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.negocio.logica.Negocio_CAPITULO;
import com.policia.negocio.modelo.Modelo_TITULO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Titulo_BaseExpandableListAdapter extends BaseExpandableListAdapter {

    private Fragment context;
    private ArrayList<Modelo_TITULO> titulos;
    private ArrayList<Detalle_CAPITULO> capitulos;

    public Titulo_BaseExpandableListAdapter(Fragment context, ArrayList<Modelo_TITULO> titulos) {

        this.context = context;

        this.titulos = titulos;
        this.capitulos = new ArrayList<Detalle_CAPITULO>();
        try {
            Negocio_CAPITULO negocio_capitulo = new Negocio_CAPITULO(context.getContext());

            for (Modelo_TITULO titulo : titulos) {

                capitulos.add(new Detalle_CAPITULO(context, titulo, negocio_capitulo.CapitulosPorTitulo(titulo.ID)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.libro_titulo, null);

        TextView textViewLabel = view.findViewById(R.id.textViewLabel);
        TextView textViewValue = view.findViewById(R.id.textViewValue);

        textViewLabel.setText(titulos.get(groupPosition).Nivel);
        textViewValue.setText(titulos.get(groupPosition).Titulo);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.libro_capitulo, null);

        TextView textViewLabel = view.findViewById(R.id.textViewLabel);
        TextView textViewValue = view.findViewById(R.id.textViewValue);

        textViewLabel.setText(capitulos.get(groupPosition).getCapitulos().get(childPosition).Nivel);
        textViewValue.setText(capitulos.get(groupPosition).getCapitulos().get(childPosition).Capitulo);

        return view;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public int getGroupCount() {

        return titulos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return capitulos.get(groupPosition).getCapitulos().size();
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return Long.parseLong(capitulos.get(groupPosition).getCapitulos().get(childPosition).ID);
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

}
