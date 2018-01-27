package com.policia.codigopolicia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.policia.codigopolicia.adapter.CustomListAdapter;

public class PortalActivity extends AppCompatActivity implements View.OnClickListener {

    ListView list;
    String[] menuList;
    String[] urlList;

    private int counter = 0;
    private ShowcaseView showcaseView;

    private final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.portal_layout);

        menuList = getResources().getStringArray(R.array.listadoMenuPsc);
        urlList = getResources().getStringArray(R.array.listadoUrlPsc);

        Integer[] imgid = {
                R.mipmap.adenunciar,
                R.mipmap.antecedentes,
                R.mipmap.extraidos,
                R.mipmap.apertura,
                R.mipmap.pqrs,
                R.mipmap.img_11_radio_policia
        };

        CustomListAdapter adapter = new CustomListAdapter(this, menuList, imgid);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.addHeaderView(getLayoutInflater().inflate(R.layout.portal_header, null), null, false);

        final Activity activity = this;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                String title = "";
                String text = "";

                counter = position - 1;
                switch (counter) {
                    case 0:
                        title = "!A Denunciar¡";
                        text = "Esta herramienta le permite tramitar su denuncia virtual y oportunamente. Personal de la policía judicial está dispuesto a agilizar su denuncia en el menor tiempo posible.";
                        break;
                    case 1:
                        title = "Consulta Antecedentes";
                        text = "El acceso a la consulta de Antecedentes Judiciales por Internet es un servicio de carácter permanente que presta la Policía Nacional de Colombia conforme a lo establecido en el artículo 94 del Decreto 019 de 2012, para que los ciudadanos puedan validar su información judicial personal.";
                        break;
                    case 2:
                        title = "Documentos Extraviados";
                        text = "Trámites de constancias por pérdida de documentos y/o elementos, ante la Policía Nacional.";
                        break;
                    case 3:
                        title = "Apertura Establecimientos";
                        text = "Es obligatorio, para el ejercicio de cualquier actividad: comercial, industrial, de servicios, social, cultural, de recreación, de entretenimiento, de diversión; con o sin ánimo de lucro, o que siendo privadas, trasciendan a lo público; que se desarrolle o no a través de establecimientos abiertos o cerrados al público.";
                        break;
                    case 4:
                        title = "Preguntas, Quejas, Reclamos y Sugerencias";
                        text = "Las oficinas de atención al ciudadano de la Policía Nacional, recepcionan, tramitan, gestionan e informan sobre las peticiones, quejas o reclamos, reconocimientos del servicio y sugerencias, como también monitorean los espacios de participación ciudadana adelantadas por la institución, facilitando al ciudadano la intervención, vigilancia y evaluación a los procesos, así mismo el acceso a consulta e información y contribuir a formar al ciudadano en la cultura de la participación.";
                        break;
                    case 5:
                        title = "Sintonizar Radio Policía Nacional";
                        text = "Sintonice y escuche la frecuencia de la Radio Policía Nacional de Colombia en la ciudad de Bogotá.";
                        break;
                }

                showcaseView = new ShowcaseView.Builder(activity)
                        .withMaterialShowcase()
                        .singleShot(Long.parseLong(position + "" + R.layout.portal_layout))
                        .setStyle(R.style.CustomShowcaseTheme2)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setOnClickListener((View.OnClickListener) activity)
                        .build();

                if (!showcaseView.isShown())
                    mostrar(false);
            }
        });

        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .singleShot(R.layout.portal_layout)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("Portal ciudadano")
                .setContentText("Gestione sus diferentes trámites ante la Policía Nacional de Colombia.")
                .build();
    }

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void openApp(String packageName) {

        startActivity(getPackageManager().getLaunchIntentForPackage(packageName));
    }

    @Override
    public void onClick(View view) {

        mostrar(showcaseView.isShown());
    }

    private void mostrar(boolean se_muestra) {

        if (counter == 0) {

            PackageManager pm = getPackageManager();
            String pn = urlList[counter];
            if (this.isPackageInstalled(pn, pm)) {
                this.openApp(pn);
            } else {
                final String appPackageName = pn; // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        } else {

            String url = urlList[counter];
            Intent intent = new Intent(PortalActivity.this, GenericWebviewActivity.class);
            Bundle b = new Bundle();
            b.putString("url", url); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
        }

        if (se_muestra) {
            showcaseView.hide();
        }
    }
}
