package com.policia.codigopolicia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.policia.codigopolicia.adapter.Capacitacion_Adapter;
import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.negocio.modelo.Modelo_DOCUMENTO;

import java.util.ArrayList;

public class CapacitacionActivity extends Activity {

    private ListView listviewCapacitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capacitacion_activity);

        try {
            Negocio_DOCUMENTO negocioDocumento = new Negocio_DOCUMENTO(getBaseContext());


            final ArrayList<Modelo_DOCUMENTO> documentos = new ArrayList<Modelo_DOCUMENTO>();
            documentos.add(new Modelo_DOCUMENTO("VIDEO CODIGO POLICIA", "https://www.youtube.com/watch?time_continue=5&v=Jn0ML-a1C14"));
            documentos.add(new Modelo_DOCUMENTO("LEY CODIGO POLICIA", "https://www.policia.gov.co/sites/default/files/ley-1801-codigo-nacional-policia-convivencia.pdf"));

            listviewCapacitacion = findViewById(R.id.listviewCapacitacion);
            listviewCapacitacion.addHeaderView(getLayoutInflater().inflate(R.layout.articulo_header, null), null, false);
            //listviewCapacitacion.setAdapter(new Capacitacion_Adapter(this, negocioDocumento.Documentos()));
            listviewCapacitacion.setAdapter(new Capacitacion_Adapter(this, documentos));
            listviewCapacitacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    Uri uri = Uri.parse(documentos.get(position - 1).URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
