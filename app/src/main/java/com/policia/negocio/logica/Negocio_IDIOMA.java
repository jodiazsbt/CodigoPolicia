package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_IDIOMA;
import com.policia.persistencia.rutinas.Rutinas_IDIOMA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 8/12/2017.
 */

public class Negocio_IDIOMA {

    private Rutinas_IDIOMA rutinasIdioma;

    public Negocio_IDIOMA(Context context) {

        rutinasIdioma = new Rutinas_IDIOMA(context);
    }

    public ArrayList<Modelo_IDIOMA> Idiomas() {

        return rutinasIdioma.Idiomas();
    }
}
