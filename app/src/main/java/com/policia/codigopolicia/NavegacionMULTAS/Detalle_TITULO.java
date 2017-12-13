package com.policia.codigopolicia.NavegacionMULTAS;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.policia.negocio.modelo.Modelo_MULTA;
import com.policia.negocio.modelo.Modelo_TITULO_MULTA;
import com.policia.negocio.modelo.ValuePar;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Detalle_TITULO {

    private Activity activity;
    private Modelo_MULTA libro;
    private ArrayList<ValuePar> detalles;
    private ArrayList<Modelo_TITULO_MULTA> titulos;

    public Detalle_TITULO(Fragment context, Modelo_MULTA libro, ArrayList<Modelo_TITULO_MULTA> titulos) {

        activity = context.getActivity();

        this.libro = libro;
        this.titulos = titulos;
        detalles = new ArrayList<ValuePar>();
        if (titulos != null) {
            for (Modelo_TITULO_MULTA titulo : titulos) {
                detalles.add(new ValuePar(titulo.Nivel, titulo.Titulo));
            }
        }
    }

    public ArrayList<Modelo_TITULO_MULTA> getTitulos() {
        return titulos;
    }

    public String getLibro() {
        return libro.Libro;
    }

    public String getNivel() {
        return libro.Nivel;
    }

    public int getCount() {
        return detalles.size();
    }

    public ValuePar getItem(int index) {
        return detalles.get(index);
    }
}
