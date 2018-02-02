package com.policia.codigopolicia.NavegacionCNPC;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.policia.codigopolicia.ArticuloCapituloActivity;
import com.policia.codigopolicia.R;
import com.policia.negocio.logica.Negocio_TITULO;
import com.policia.negocio.modelo.Modelo_LIBRO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Nivel_BaseExpandableListAdapter extends BaseExpandableListAdapter {

    private Fragment context;
    private ArrayList<Modelo_LIBRO> libros;
    private ArrayList<Detalle_TITULO> titulos;

    private Negocio_TITULO negocio_titulo;

    public Nivel_BaseExpandableListAdapter(Fragment context, ArrayList<Modelo_LIBRO> libros) {

        this.context = context;

        this.libros = libros;
        this.titulos = new ArrayList<Detalle_TITULO>();

        try {
            negocio_titulo = new Negocio_TITULO(context.getContext());

            for (Modelo_LIBRO libro : libros) {

                titulos.add(new Detalle_TITULO(context, libro, negocio_titulo.TitulosPorLibro(libro.ID)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.libro_adapter, null, true);

        TextView textViewLabel = view.findViewById(R.id.textViewLabel);
        TextView textViewValue = view.findViewById(R.id.textViewValue);
        ImageView imageViewLibro = view.findViewById(R.id.imageViewLibro);

        Modelo_LIBRO libro = libros.get(groupPosition);

        textViewLabel.setText(libro.Nivel);
        textViewValue.setText(libro.Libro);
        imageViewLibro.setImageDrawable(
                context.getResources().getDrawable(
                        context.getResources().getIdentifier(
                                libro.Recurso,
                                null,
                                context.getActivity().getPackageName())));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        Titulo_ExpandableListView titulo_expandableListView = new Titulo_ExpandableListView(context.getContext());
        titulo_expandableListView.setAdapter(new Titulo_BaseExpandableListAdapter(context, titulos.get(groupPosition).getTitulos()));
        //titulo_expandableListView.setGroupIndicator(null);

        titulo_expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(view.getContext(), ArticuloCapituloActivity.class);
                intent.putExtra("capitulo", id);
                intent.putExtra("posicion", 0);//primera página
                context.startActivity(intent);

                return true;
            }
        });

        return titulo_expandableListView;
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

        return libros.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

}
