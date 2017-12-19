package com.policia.codigopolicia.NavegacionMULTAS;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by 1085253556 on 1/12/2017.
 */

public class MULTAS_FragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private int paginas;
    private String multa;
    private String categoria;

    public MULTAS_FragmentStatePagerAdapter(FragmentManager fm, String multa, String categoria, int paginas) {
        super(fm);

        this.paginas = paginas;
        this.multa = multa;
        this.categoria = categoria;
    }

    @Override
    public Fragment getItem(int position) {

        return MULTAS_Fragment.newInstance(position, multa, categoria);
    }

    @Override
    public int getCount() {

        return paginas;
    }
}
