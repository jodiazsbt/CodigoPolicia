package com.policia.codigopolicia.NavegacionMULTAS;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.ArgisActivity;
import com.policia.codigopolicia.R;
import com.policia.negocio.logica.Negocio_CATEGORIA;
import com.policia.negocio.modelo.Modelo_CATEGORIA;
import com.policia.negocio.modelo.Modelo_MULTA;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Multa_BaseExpandableListAdapter extends BaseExpandableListAdapter {

    private Fragment context;
    private ArrayList<Modelo_MULTA> multas;
    private ArrayList<Detalle_CATEGORIA> categorias;

    private Negocio_CATEGORIA negocio_categoria;

    public Multa_BaseExpandableListAdapter(Fragment context, ArrayList<Modelo_MULTA> multas) {

        this.context = context;

        this.multas = multas;
        this.categorias = new ArrayList<Detalle_CATEGORIA>();

        try {
            negocio_categoria = new Negocio_CATEGORIA(context.getContext());

            for (Modelo_MULTA multa : multas) {

                categorias.add(new Detalle_CATEGORIA(context, multa, negocio_categoria.CategoriaPorTipoMulta(multa.ID)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.multa_adapter, null, true);

        TextView textViewLabel = view.findViewById(R.id.textViewLabel);
        TextView textViewValue = view.findViewById(R.id.textViewValue);

        String multa = multas.get(groupPosition).Multa;
        Double valor = multas.get(groupPosition).Valor;

        textViewLabel.setText(multas.get(groupPosition).Nivel);

        if (valor == 0)
            textViewValue.setText(multa);
        else
            textViewValue.setText(multa + " " + NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(valor));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.multa_titulo, null);

        TextView textViewLabel = view.findViewById(R.id.textViewLabel);
        //TextView textViewValue = view.findViewById(R.id.textViewValue);

        textViewLabel.setText(categorias.get(groupPosition).getCategorias().get(childPosition).Categoria);
        //textViewValue.setText(categorias.get(groupPosition).getCategorias().get(childPosition).Categoria);

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

        return multas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return categorias.get(groupPosition).getCategorias().size();
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return Long.parseLong(categorias.get(groupPosition).getCategorias().get(childPosition).ID);
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    public String MultaID(int groupPosition) {
        return multas.get(groupPosition).ID;
    }
}
