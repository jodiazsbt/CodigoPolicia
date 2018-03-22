package com.policia.codigopolicia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.policia.codigopolicia.adapter.Capacitacion_Adapter;
import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.negocio.modelo.Modelo_DOCUMENTO;

import java.net.URL;
import java.util.ArrayList;

/**
* CapacitacionActivity:
*
* Esta actividad lista los materiales de consulta disponibles para el personal de policia
* */
public class CapacitacionActivity extends Activity {

    private ListView listviewCapacitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capacitacion_activity);

        try {
            Negocio_DOCUMENTO negocioDocumento = new Negocio_DOCUMENTO(getBaseContext());

            final ArrayList<Modelo_DOCUMENTO> documentos = negocioDocumento.Documentos();

            /*
            documentos.add(new Modelo_DOCUMENTO("VIDEO CODIGO POLICIA", "https://www.youtube.com/watch?time_continue=5&v=Jn0ML-a1C14"));
            documentos.add(new Modelo_DOCUMENTO("LEY CODIGO POLICIA", "https://www.policia.gov.co/sites/default/files/ley-1801-codigo-nacional-policia-convivencia.pdf"));*/
            listviewCapacitacion = findViewById(R.id.listviewCapacitacion);


            View header = getLayoutInflater().inflate(R.layout.articulo_header, null);
            new Negocio_DOCUMENTO(getBaseContext()).drawAVATAR(Negocio_DOCUMENTO.AVATAR.SCREEN_CAPACITACION,
                    (ImageView) header.findViewById(R.id.imageViewCaricatura));
            listviewCapacitacion.addHeaderView(header, null, false);

            listviewCapacitacion.setAdapter(new Capacitacion_Adapter(this, documentos));
            //listviewCapacitacion.setAdapter(new Capacitacion_Adapter(this, documentos));
            listviewCapacitacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    String url = documentos.get(position - 1).URL;

                    if (validateHTTP_URI(url)) {

                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CapacitacionActivity.this, "Dirección del recurso no es válida", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateHTTP_URI(String uri) {
        final URL url;
        try {
            url = new URL(uri);
        } catch (Exception e1) {
            return false;
        }
        return "https".equals(url.getProtocol());
    }
}
