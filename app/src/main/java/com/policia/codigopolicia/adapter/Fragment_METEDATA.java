package com.policia.codigopolicia.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.policia.codigopolicia.ArticuloActivity;
import com.policia.codigopolicia.R;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.negocio.logica.Negocio_METADATA;
import com.policia.negocio.modelo.Modelo_Busqueda_Articulo;

/**
 * Created by JORGE on 3/12/2017.
 */

public class Fragment_METEDATA extends Fragment implements IActualizarListadoBusqueda {

    private ListView listViewBusqueda;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.busqueda_fragment, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle state) {
        super.onActivityCreated(state);

        listViewBusqueda = getView().findViewById(R.id.listViewBusqueda);
        listViewBusqueda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                Intent intent = new Intent(view.getContext(), ArticuloActivity.class);
                intent.putExtra("capitulo", id);
                intent.putExtra("posicion", ((Modelo_Busqueda_Articulo) adapterView.getItemAtPosition(pos)).PosicionArticulo);
                startActivity(intent);

            }
        });
    }

    @Override
    public void actualizar(String termino_busqueda) {
        Busqueda_Adapter adapter = null;
        try {
            adapter = new Busqueda_Adapter(this, new Negocio_METADATA(getContext()).BusquedaMETADATA(termino_busqueda));
            listViewBusqueda.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
