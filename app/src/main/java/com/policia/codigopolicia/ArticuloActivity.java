package com.policia.codigopolicia;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;

import com.policia.codigopolicia.NavegacionCNPC.CNPC_FragmentStatePagerAdapter;
import com.policia.codigopolicia.NavegacionCNPC.WrapContentViewPager;
import com.policia.negocio.logica.Negocio_ARTICULO;

public class ArticuloActivity extends FragmentActivity {

    WrapContentViewPager viewPagerArticulos;
    PagerAdapter pagerAdapterArticulo;

    private int posicion;
    private String capitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articulo_activity);

        posicion = getIntent().getExtras().getInt("posicion");
        capitulo = getIntent().getExtras().get("capitulo").toString();

        try {
            int paginas = 0;
            paginas = new Negocio_ARTICULO(this).CantidadArticulosPorCapitulo(capitulo);

            viewPagerArticulos = findViewById(R.id.viewPagerArticulos);
            pagerAdapterArticulo = new CNPC_FragmentStatePagerAdapter(getSupportFragmentManager(), capitulo, paginas);
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
