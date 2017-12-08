package com.policia.codigopolicia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_Busqueda_Articulo;

import java.util.ArrayList;

/**
 * Created by JORGE on 3/12/2017.
 */

public class Busqueda_Adapter extends BaseAdapter {

    public Fragment_METEDATA context;
    public ArrayList<Modelo_Busqueda_Articulo> busquedaArticulos;

    public Busqueda_Adapter(Fragment_METEDATA context, ArrayList<Modelo_Busqueda_Articulo> articulos) {
        this.context = context;
        this.busquedaArticulos = articulos;
    }

    @Override
    public int getCount() {

        return this.busquedaArticulos.size();
    }

    @Override
    public Object getItem(int position) {

        return this.busquedaArticulos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return Long.parseLong(busquedaArticulos.get(position).CapituloID);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.busqueda_capitulo, null);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.textLIBRO = (TextView) rowView.findViewById(R.id.textLIBRO);
            viewHolder.textTITULO = (TextView) rowView.findViewById(R.id.textTITULO);
            viewHolder.textCAPITULO = (TextView) rowView.findViewById(R.id.textCAPITULO);
            viewHolder.textARTICULO = (TextView) rowView.findViewById(R.id.textARTICULO);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textLIBRO.setText(busquedaArticulos.get(position).Libro);
        viewHolder.textTITULO.setText(busquedaArticulos.get(position).Titulo);
        viewHolder.textCAPITULO.setText(busquedaArticulos.get(position).Capitulo);
        viewHolder.textARTICULO.setText(busquedaArticulos.get(position).Articulo);
        return rowView;
    }
}
