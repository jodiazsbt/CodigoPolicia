package com.policia.codigopolicia.IdentificacionPolicia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.policia.codigopolicia.R;

/**
 * Created by 1085253556 on 21/12/2017.
 */

public class Fragment_Opciones extends Fragment implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;

    private IClickScan iClickScan;

    public static Fragment_Opciones newInstance(IClickScan clickScan) {
        Fragment_Opciones fragment = new Fragment_Opciones();
        fragment.iClickScan = clickScan;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.policia_fragment_opciones, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        autoFocus = (CompoundButton) getView().findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) getView().findViewById(R.id.use_flash);

        getView().findViewById(R.id.buttonAceptar).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonAceptar) {
            // launch barcode activity.
            iClickScan.ClickScan(
                    v,
                    autoFocus.isChecked(),
                    useFlash.isChecked());
        }
    }
}
