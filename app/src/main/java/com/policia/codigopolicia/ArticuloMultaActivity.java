package com.policia.codigopolicia;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;

import com.policia.codigopolicia.NavegacionCNPC.WrapContentViewPager;
import com.policia.codigopolicia.NavegacionMULTAS.MULTAS_FragmentStatePagerAdapter;
import com.policia.negocio.logica.Negocio_ARTICULO;

/**
 * Created by 1085253556 on 19/12/2017.
 */

public class ArticuloMultaActivity extends FragmentActivity {

    WrapContentViewPager viewPagerArticulos;
    PagerAdapter pagerAdapterArticulo;

    private int posicion;
    private String multa;
    private String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articulo_activity);

        multa = getIntent().getExtras().get("multa").toString();
        posicion = getIntent().getExtras().getInt("posicion");
        categoria = getIntent().getExtras().get("categoria").toString();

        try {
            int paginas = 0;
            paginas = new Negocio_ARTICULO(this).CantidadArticulosPorMultaCategoria(multa, categoria);

            viewPagerArticulos = findViewById(R.id.viewPagerArticulos);
            pagerAdapterArticulo = new MULTAS_FragmentStatePagerAdapter(getSupportFragmentManager(), multa, categoria, paginas);
            viewPagerArticulos.setAdapter(pagerAdapterArticulo);
            viewPagerArticulos.setCurrentItem(posicion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
        if (viewPagerArticulos.getCurrentItem() == 0) {
            //salir si no tiene mas paginas para visualizar
            super.onBackPressed();
        } else {
            //devolverse si tiene mas paginas para visualizar
            viewPagerArticulos.setCurrentItem(viewPagerArticulos.getCurrentItem() - 1);
        }
        */
    }
}