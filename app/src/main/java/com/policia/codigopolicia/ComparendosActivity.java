package com.policia.codigopolicia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.policia.codigopolicia.Comparendos.FragmentComparendoConsulta;
import com.policia.codigopolicia.Comparendos.FragmentComparendoExpediente;
import com.policia.remote.response.RNMCGENERALResponse;

public class ComparendosActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparendos_activity);

        inflarFragmentoConsulta();
        getSupportActionBar().setTitle(getResources().getString(R.string.menu_funcionario));
    }

    public void inflarFragmentoConsulta() {

        fragment = FragmentComparendoConsulta.newInstance(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    public void inflarFragmentoExpediente(RNMCGENERALResponse expediente) {

        fragment = FragmentComparendoExpediente.newInstance(this, expediente);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    public void clickCancelar(View v) {

        finish();
    }
}
