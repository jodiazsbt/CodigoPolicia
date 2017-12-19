package com.policia.codigopolicia.NavegacionMULTAS;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.policia.negocio.modelo.Modelo_CATEGORIA;
import com.policia.negocio.modelo.Modelo_MULTA;
import com.policia.negocio.modelo.ValuePar;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Detalle_CATEGORIA {

    private Activity activity;
    private Modelo_MULTA multa;
    private ArrayList<ValuePar> detalles;
    private ArrayList<Modelo_CATEGORIA> categorias;

    public Detalle_CATEGORIA(Fragment context, Modelo_MULTA multa, ArrayList<Modelo_CATEGORIA> categorias) {

        activity = context.getActivity();

        this.multa = multa;
        this.categorias = categorias;
        detalles = new ArrayList<ValuePar>();
        if (categorias != null) {
            for (Modelo_CATEGORIA categoria : categorias) {
                detalles.add(new ValuePar(categoria.Categoria, ""));
            }
        }
    }

    public ArrayList<Modelo_CATEGORIA> getCategorias() {
        return categorias;
    }

    public String getMulta() {
        return multa.Multa;
    }

    public String getNivel() {
        return multa.Nivel;
    }

    public int getCount() {
        return detalles.size();
    }

    public ValuePar getItem(int index) {
        return detalles.get(index);
    }
}
