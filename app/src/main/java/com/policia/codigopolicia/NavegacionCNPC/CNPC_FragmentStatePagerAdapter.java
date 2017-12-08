package com.policia.codigopolicia.NavegacionCNPC;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by 1085253556 on 1/12/2017.
 */

public class CNPC_FragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private int paginas;
    private String capitulo;

    public CNPC_FragmentStatePagerAdapter(FragmentManager fm, String capitulo, int paginas) {
        super(fm);

        this.paginas = paginas;
        this.capitulo = capitulo;
    }

    @Override
    public Fragment getItem(int position) {

        return CNPC_Fragment.newInstance(position, capitulo);
    }

    @Override
    public int getCount() {

        return paginas;
    }
}
