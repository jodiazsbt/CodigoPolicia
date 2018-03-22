package com.policia.codigopolicia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.policia.codigopolicia.Comparendos.FragmentComparendoConsulta;
import com.policia.codigopolicia.Comparendos.FragmentComparendoExpediente;
import com.policia.codigopolicia.Comparendos.FragmentoComparendoCedula;
import com.policia.codigopolicia.Comparendos.FragmentoComparendoEsperando;
import com.policia.remote.response.RNMCGENERAL2Response;
import com.policia.remote.response.RNMCGENERALResponse;

/**
* ComparendosActivity:
*
* Esta actividad se encarga de realizar la consulta de comparendos
* que se le impusieron a un ciudadano
* */
public class ComparendosActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparendos_activity);

        inflarFragmentoConsulta();
        getSupportActionBar().setTitle(getResources().getString(R.string.menu_funcionario));
    }

    /**
    * Este metodo infla el fragmento de consulta
    * */
    public void inflarFragmentoConsulta() {

        fragment = FragmentComparendoConsulta.newInstance(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    /**
     * Este metodo infla el fragmento de los expedientes encontrados
     * */
    public void inflarFragmentoExpediente(RNMCGENERALResponse expediente) {

        fragment = FragmentComparendoExpediente.newInstance(this, expediente);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    /**
     * Este metodo infla el fragmento de los expedientes encontrados
     * */
    public void inflarFragmentoCedula(RNMCGENERAL2Response expediente) {

        fragment = FragmentoComparendoCedula.newInstance(this, expediente);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    /**
     * Este metodo infla el fragmento de espera
     * */
    public void inflarFragmentoEsperando() {

        fragment = new FragmentoComparendoEsperando();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    /**
     * Este metodo devuelve al usuario a la actividad principal
     * */
    public void clickCancelar(View v) {

        finish();
    }
}
