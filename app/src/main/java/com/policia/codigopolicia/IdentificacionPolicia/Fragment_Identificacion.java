package com.policia.codigopolicia.IdentificacionPolicia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.policia.codigopolicia.R;
import com.policia.remote.RemotePolicia;

/**
 * Created by 1085253556 on 21/12/2017.
 */

public class Fragment_Identificacion extends Fragment {

    protected IDisparadorLectura lectura;

    public static Fragment_Identificacion newInstance(IDisparadorLectura lectura) {

        Fragment_Identificacion fragment = new Fragment_Identificacion();
        fragment.lectura = lectura;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.policia_fragment_identificacion, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void mostrarCarnet(String barcode) {
        //barcode = "Codigo de barras generado por Robotec, fecha 19/11/2016 11:19:48 a.m.<DocumentElement><Table><E2>79998944</E2><E4>OLAYA POLOCHE</E4><E5>CESAR AUGUSTO</E5><E8>2021-07-25T12:00:00-05:00</E8><E11>SUBINTENDENTE</E11><E13>1978-07-25T00:00:00-05:00</E13><E15>94169484</E15><E16>O </E16><E19>MA</E19><E21>CC</E21><E22>PO</E22><E25>79998944/2016/11/19:11:08:24</E25></Table></DocumentElement>";
        ListView listviewIdentificacion = getView().findViewById(R.id.listviewIdentificacion);
        try {

            RemotePolicia.newInstance(this, listviewIdentificacion, barcode, lectura).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IDisparadorLectura {

        public void realizarLectura(RemotePolicia.MotivoErrorLectura lectura);
    }
}
