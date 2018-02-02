package com.policia.codigopolicia.Comparendos;

import com.policia.remote.response.RNMCGENERALResponse;

/**
 * Created by 1085253556 on 1/02/2018.
 */

public interface IComparendoExpediente {

    void consultar(RNMCGENERALResponse expediente, String TipoDocumento, String Identificacion);
}
