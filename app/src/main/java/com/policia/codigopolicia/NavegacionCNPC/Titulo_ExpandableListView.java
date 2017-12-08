package com.policia.codigopolicia.NavegacionCNPC;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Titulo_ExpandableListView extends ExpandableListView {

    public Titulo_ExpandableListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
