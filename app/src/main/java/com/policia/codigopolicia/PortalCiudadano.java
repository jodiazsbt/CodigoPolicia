package com.policia.codigopolicia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.policia.codigopolicia.adapter.CustomListAdapter;

/**
 * Created by 1085253556 on 11/12/2017.
 */

public class PortalCiudadano extends Activity {

    ListView list;
    String[] menuList;
    String[] urlList;


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

        };

        CustomListAdapter adapter = new CustomListAdapter(this, menuList, imgid);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = urlList[position];
                Intent intent = new Intent(PortalCiudadano.this, GenericWebviewActivity.class);
                Bundle b = new Bundle();
                b.putString("url", url); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);

            }
        });
    }
}
